plugins {
    id(Plugins.ANDROID_LIB_PLUGIN_WITH_DEFAULT_CONFIG)
}

dependencies {
    androidX()
    fragment()
    lifecycle()

    dagger()

    coroutines()
}
