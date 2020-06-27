plugins {
    id(Plugins.ANDROID_LIB_PLUGIN_WITH_DEFAULT_CONFIG)
}

dependencies {
    mvi()
    materialDesign()
    recyclerView()
    implementationModule(Module.RoboSwag.KOTLIN_EXTENSIONS)
    implementationModule(Module.RoboSwag.VIEWS)
    implementationModule(Module.RoboSwag.UTILS)
}
