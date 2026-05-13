package io.github.movieboxhook

object Constants {
    const val MAX_RETRIES = 20
    const val INITIAL_DELAY_MS = 3000L
    const val RETRY_DELAY_MS = 1000L
    const val EMERGENCY_HOOK_METHOD_LIMIT = 20
    const val FRESH_INSTALL_WINDOW_MS = 10L * 60L * 1000L // 10 minutes

    val TARGET_PACKAGES: Set<String> = setOf(
        "com.community.oneroom",
        "com.community.mbox.in"
    )
}
