plugins {
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.agp.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
