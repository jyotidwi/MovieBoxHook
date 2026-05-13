package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils

object MemberBriefInfoHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val c = HookUtils.findClassIfExists("com.transsion.member.bean.MemberBriefInfo", cl)
                ?: return false

            module.hook(c.getDeclaredMethod("isActive")).intercept { true }
            module.hook(c.getDeclaredMethod("getExpiryDate")).intercept { "2099-01-01" }
            module.hook(c.getDeclaredMethod("getMemberType")).intercept { 2 }

            Logger.success("✓ MemberBriefInfo hooks applied (3 methods)")
            true
        } catch (t: Throwable) {
            Logger.error("MemberBriefInfo hooks failed: ${t.message}")
            false
        }
    }
}
