package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils

object MemberInfoHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val c = HookUtils.findClassIfExists("com.transsion.memberapi.MemberInfo", cl)
                ?: return false

            module.hook(c.getDeclaredMethod("isActive")).intercept { true }
            module.hook(c.getDeclaredMethod("getExpiryDate")).intercept { "2099-01-01" }
            module.hook(c.getDeclaredMethod("getDaysLeft")).intercept { 9999 }
            module.hook(c.getDeclaredMethod("getMemberType")).intercept { 2 }
            module.hook(c.getDeclaredMethod("getNextRenewDate")).intercept { "2099-01-01" }

            Logger.success("✓ MemberInfo hooks applied (5 methods)")
            true
        } catch (t: Throwable) {
            Logger.error("MemberInfo hooks failed: ${t.message}")
            false
        }
    }
}
