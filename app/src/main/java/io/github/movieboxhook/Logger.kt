package io.github.movieboxhook

import android.util.Log
import io.github.libxposed.api.XposedModule

object Logger {
    var module: XposedModule? = null

    private fun log(msg: String) {
        module?.log(Log.INFO, "MovieboxHook", msg)
        Log.i("MovieboxHook", msg)
    }

    fun success(msg: String) = log("✅ $msg")
    fun error(msg: String) = log("❌ $msg")
    fun warn(msg: String) = log("⚠️ $msg")
    fun info(msg: String) = log("ℹ️ $msg")
    fun debug(msg: String) = log("🐛 $msg")
}
