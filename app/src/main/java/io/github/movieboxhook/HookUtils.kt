package io.github.movieboxhook

import android.os.Handler
import android.os.Looper

object HookUtils {
    fun findClassIfExists(name: String, cl: ClassLoader): Class<*>? {
        return try {
            Class.forName(name, false, cl)
        } catch (ignored: Throwable) {
            null
        }
    }

    private val isMainLooperReady: Boolean
        get() = Looper.getMainLooper() != null

    fun postDelayed(r: Runnable, delayMs: Long) {
        if (isMainLooperReady) {
            Handler(Looper.getMainLooper()).postDelayed(r, delayMs)
        } else {
            Thread {
                safeSleep(delayMs)
                r.run()
            }.start()
        }
    }

    fun safeSleep(ms: Long) {
        try {
            Thread.sleep(ms)
        } catch (ignored: InterruptedException) {}
    }
}
