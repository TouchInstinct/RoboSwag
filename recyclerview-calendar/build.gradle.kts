plugins {
    id("com.android.library")
}

val versions: Map<String, *> by rootProject.extra
android {
    compileSdkVersion(versions["compileSdk"] as Int)

    defaultConfig {
        minSdkVersion(16)
    }
}

dependencies {
    api(project(":logging"))
    api("net.danlew:android.joda:2.9.9.4")

    implementation("androidx.recyclerview:recyclerview:${versions["androidx"]}")
}
