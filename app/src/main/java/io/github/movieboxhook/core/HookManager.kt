package io.github.movieboxhook.core

import android.app.AndroidAppHelper
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Looper
import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Constants
import io.github.movieboxhook.HookUtils
import io.github.movieboxhook.Logger
import io.github.movieboxhook.hooks.*
import kotlin.math.max
import kotlin.math.min

class HookManager(private val module: XposedModule) {

    private var hooksApplied = false
    private var lastInstallTime: Long = 0

    fun handleLoadPackage(packageName: String, processName: String, classLoader: ClassLoader) {
        if (!Constants.TARGET_PACKAGES.contains(packageName)) return

        Logger.success("🎯 Target package loaded: \$packageName | Process: \$processName")

        checkAppInstallTime(packageName)

        hooksApplied = false

        val starter = Runnable { attemptHooks(classLoader, 0, packageName) }
        try {
            if (Looper.getMainLooper() != null) {
                HookUtils.postDelayed(starter, Constants.INITIAL_DELAY_MS)
            } else {
                Thread {
                    HookUtils.safeSleep(Constants.INITIAL_DELAY_MS)
                    starter.run()
                }.start()
            }
        } catch (t: Throwable) {
            Thread(starter).start()
        }
    }

    private fun checkAppInstallTime(packageName: String) {
        try {
            val context: Context? = AndroidAppHelper.currentApplication()
            if (context == null) {
                Logger.warn("Context null, skipping install time check")
                return
            }

            val pm: PackageManager = context.packageManager
            val pi: PackageInfo = pm.getPackageInfo(packageName, 0)

            val install = pi.firstInstallTime
            val update = pi.lastUpdateTime
            val newest = max(install, update)

            if (System.currentTimeMillis() - newest < 5 * 60 * 1000) {
                Logger.warn("📱 App recently installed/updated - enabling aggressive hook mode")
                lastInstallTime = newest
            }
        } catch (t: Throwable) {
            Logger.debug("Install time check failed: \${t.message}")
        }
    }

    private fun isFreshInstall(): Boolean {
        return lastInstallTime > 0 &&
                (System.currentTimeMillis() - lastInstallTime) < Constants.FRESH_INSTALL_WINDOW_MS
    }

    private fun calculateNextDelay(attempt: Int): Long {
        if (isFreshInstall()) {
            return min(Constants.RETRY_DELAY_MS * (attempt + 1), 5000L)
        }
        return Constants.RETRY_DELAY_MS
    }

    private fun checkKeyClasses(cl: ClassLoader): Boolean {
        return HookUtils.findClassIfExists("com.transsion.memberapi.MemberInfo", cl) != null ||
               HookUtils.findClassIfExists("com.transsion.member.bean.MemberBriefInfo", cl) != null
    }

    private fun checkAllCriticalClasses(cl: ClassLoader): Boolean {
        val classes = arrayOf(
            "com.transsion.memberapi.MemberInfo",
            "com.transsion.member.bean.MemberBriefInfo",
            "com.transsion.member.MemberProvider",
            "com.transsion.memberapi.MemberCheckResult",
            "com.transsnet.loginapi.bean.UserInfo"
        )

        var found = 0
        for (c in classes) {
            if (HookUtils.findClassIfExists(c, cl) != null) found++
        }

        Logger.debug("Found \$found/\${classes.size} critical classes")
        return found >= 3
    }

    private fun attemptHooks(cl: ClassLoader, attempt: Int, packageName: String) {
        if (hooksApplied) {
            Logger.debug("Hooks already applied, skipping")
            return
        }

        try {
            Logger.info("🔄 Hook attempt \${attempt + 1}/\${Constants.MAX_RETRIES}")

            if (isFreshInstall()) {
                if (!checkAllCriticalClasses(cl)) throw ClassNotFoundException("Not all classes loaded yet")
            } else {
                if (!checkKeyClasses(cl)) throw ClassNotFoundException("Key classes missing")
            }

            Logger.success("📚 Classes loaded, applying hooks...")

            var ok = 0
            val total = 7

            ok += if (MemberInfoHook.apply(module, cl)) 1 else 0
            ok += if (MemberBriefInfoHook.apply(module, cl)) 1 else 0
            ok += if (MemberProviderHook.apply(module, cl)) 1 else 0
            ok += if (SkipAdHook.apply(module, cl)) 1 else 0
            ok += if (DownloadsHook.apply(module, cl)) 1 else 0
            ok += if (MemberCheckResultHook.apply(module, cl)) 1 else 0
            ok += if (UserInfoHook.apply(module, cl)) 1 else 0

            if (ok == total) {
                Logger.success("🎉 ALL hooks active: \$ok/\$total")
                hooksApplied = true
            } else if (ok >= 5) {
                Logger.success("✅ Partial success: \$ok/\$total")
                hooksApplied = true
            } else {
                Logger.warn("⚠️ Insufficient hook success: \$ok/\$total")
                throw Exception("Not enough hooks applied")
            }

        } catch (e: Throwable) {
            Logger.error("Hook attempt \${attempt + 1} failed: \${e.message}")
            if (attempt < Constants.MAX_RETRIES - 1) {
                val delay = calculateNextDelay(attempt)
                Logger.info("⏳ Retrying in \${delay}ms...")
                HookUtils.postDelayed({ attemptHooks(cl, attempt + 1, packageName) }, delay)
            } else {
                Logger.error("❌ Max retries reached - Emergency mode active")
                EmergencyHooker.apply(module, cl, Constants.EMERGENCY_HOOK_METHOD_LIMIT)
                hooksApplied = true
            }
        }
    }
}
