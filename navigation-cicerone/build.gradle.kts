plugins {
    id(Plugins.ANDROID_LIB_PLUGIN_WITH_DEFAULT_CONFIG)
}

dependencies {
    implementationModule(Module.Core.DI)
    implementation(Library.CICERONE)
    fragment()
    dagger(withAssistedInject = false)
}
