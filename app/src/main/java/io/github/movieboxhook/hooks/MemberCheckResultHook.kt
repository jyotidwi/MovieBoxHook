package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils

object MemberCheckResultHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val c = HookUtils.findClassIfExists("com.transsion.memberapi.MemberCheckResult", cl)
                ?: return false

            val methods = arrayOf("getVipEnable", "getVipPayEnable", "isPassed")
            var successCount = 0

            for (mName in methods) {
                try {
                    module.hook(c.getDeclaredMethod(mName)).intercept { true }
                    successCount++
                } catch (ignored: Throwable) {}
            }

            Logger.success("✓ MemberCheckResult hooks applied ($successCount/${methods.size} methods)")
            successCount > 0
        } catch (t: Throwable) {
            Logger.error("MemberCheckResult hooks failed: ${t.message}")
            false
        }
    }
}
