package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils
import java.lang.reflect.Field

object UserInfoHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val targetClass = "com.transsnet.loginapi.bean.UserInfo"
            val c = HookUtils.findClassIfExists(targetClass, cl)
            if (c == null) {
                Logger.warn("UserInfo class not found")
                return false
            }

            // Force nickname return
            module.hook(c.getDeclaredMethod("getNickname")).intercept { "Hooked with ❤️ by JyotiDwi " }

            // Force set field after object creation
            for (constructor in c.declaredConstructors) {
                module.hook(constructor).intercept { chain ->
                    chain.proceed()
                    try {
                        val nicknameField: Field = c.getDeclaredField("nickname")
                        nicknameField.isAccessible = true
                        nicknameField.set(chain.thisObject, "Hooked by JyotiDwi")
                    } catch (ignored: Throwable) {}
                }
            }

            Logger.success("✓ UserInfo hooks applied")
            true
        } catch (t: Throwable) {
            Logger.error("UserInfo hook failed: \${t.message}")
            false
        }
    }
}
