package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils

object SkipAdHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val mmkvClass = HookUtils.findClassIfExists("com.tencent.mmkv.MMKV", cl)
            if (mmkvClass == null) {
                Logger.warn("SkipAd: MMKV class not found")
                return false
            }

            val getBooleanMethod = mmkvClass.getDeclaredMethod("getBoolean", String::class.java, Boolean::class.javaPrimitiveType)

            module.hook(getBooleanMethod).intercept { chain ->
                val key = chain.args[0] as? String
                if (key != null && key.equals("kv_is_skip_ad", ignoreCase = true)) {
                    Logger.success("SkipAd: Forced TRUE for key → \$key")
                    return@intercept true
                }
                chain.proceed()
            }

            Logger.success("✓ SkipAdHook applied via MMKV.getBoolean()")
            true
        } catch (t: Throwable) {
            Logger.error("SkipAdHook failed: \${t.message}")
            false
        }
    }
}
