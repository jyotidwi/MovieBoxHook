package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils
import java.lang.reflect.Modifier

object EmergencyHooker {
    fun apply(module: XposedModule, cl: ClassLoader, limitPerClass: Int) {
        Logger.warn("🚨 Emergency hook mode enabled — attempting generic fallback hooks")

        val classes = arrayOf(
            "com.transsion.memberapi.MemberInfo",
            "com.transsion.member.bean.MemberBriefInfo",
            "com.transsion.memberapi.MemberCheckResult"
        )

        for (className in classes) {
            try {
                val c = HookUtils.findClassIfExists(className, cl)
                if (c != null) {
                    hookBooleanMethodsWithLimit(module, c, limitPerClass)
                    Logger.debug("Emergency hooks applied to: \$className")
                }
            } catch (ignored: Throwable) {}
        }
    }

    private fun hookBooleanMethodsWithLimit(module: XposedModule, targetClass: Class<*>, limit: Int) {
        try {
            val methods = targetClass.declaredMethods
            var hookedCount = 0

            for (m in methods) {
                if (hookedCount >= limit) break

                if (Modifier.isPublic(m.modifiers) &&
                    m.returnType == Boolean::class.javaPrimitiveType &&
                    m.parameterCount <= 2
                ) {
                    try {
                        module.hook(m).intercept { true }
                        hookedCount++
                    } catch (ignored: Throwable) {}
                }
            }
        } catch (ignored: Throwable) {}
    }
}
