package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils

object MemberProviderHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val mmkvClass = HookUtils.findClassIfExists("com.tencent.mmkv.MMKV", cl)
            if (mmkvClass == null) {
                Logger.warn("MemberProviderHook: MMKV class not found")
                return false
            }

            val getBooleanMethod = mmkvClass.getDeclaredMethod("getBoolean", String::class.java, Boolean::class.javaPrimitiveType)

            module.hook(getBooleanMethod).intercept { chain ->
                val key = chain.args[0] as? String
                if (key != null) {
                    when (key) {
                        "kv_is_enable_member", "kv_is_pay_enable_member", "kv_is_skip_ad" -> {
                            Logger.success("Force TRUE → \$key")
                            return@intercept true
                        }
                    }
                }
                chain.proceed()
            }

            Logger.success("✓ MemberProviderHook applied via MMKV.getBoolean()")
            true
        } catch (t: Throwable) {
            Logger.error("MemberProviderHook failed: \${t.message}")
            false
        }
    }
}
