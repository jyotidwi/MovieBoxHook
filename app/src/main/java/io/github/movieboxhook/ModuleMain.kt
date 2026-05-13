package io.github.movieboxhook

import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam
import io.github.movieboxhook.core.HookManager

class ModuleMain : XposedModule() {

    override fun onModuleLoaded(param: ModuleLoadedParam) {
        Logger.module = this
        Logger.info("MovieboxHook module loaded: \${param.processName}")
    }

    override fun onPackageLoaded(param: PackageLoadedParam) {
        val hookManager = HookManager(this)
        hookManager.handleLoadPackage(param.packageName, param.packageName, param.defaultClassLoader)
    }
}
