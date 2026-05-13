package io.github.movieboxhook.hooks

import io.github.libxposed.api.XposedModule
import io.github.movieboxhook.Logger
import io.github.movieboxhook.HookUtils

object DownloadsHook {
    fun apply(module: XposedModule, cl: ClassLoader): Boolean {
        return try {
            val classes = arrayOf(
                "com.transsion.baselib.db.download.DownloadBean",
                "com.transsion.moviedetailapi.DownloadItem",
                "com.transsion.moviedetailapi.bean.DownloadResolutionItem",
                "com.transsion.shorttv.bean.DownloadItem"
            )

            var successCount = 0

            for (className in classes) {
                try {
                    val clazz = HookUtils.findClassIfExists(className, cl)
                    if (clazz != null) {
                        module.hook(clazz.getDeclaredMethod("getRequireMemberType")).intercept { 0 }
                        successCount++
                    }
                } catch (ignored: Throwable) {}
            }

            Logger.success("✓ Download hooks applied (\$successCount/\${classes.size} classes)")
            successCount > 0
        } catch (t: Throwable) {
            Logger.error("DownloadsHook failed: \${t.message}")
            false
        }
    }
}
