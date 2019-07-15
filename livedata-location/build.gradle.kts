import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
}

val versions: Map<String, *> by rootProject.extra
val buildScriptsDir: String by rootProject.extra
android {
    compileSdkVersion(versions["compileSdk"] as Int)

    defaultConfig {
        minSdkVersion(16)
    }

}

dependencies {
    api(project(":lifecycle"))

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("com.google.android.gms:play-services-location:${versions["location"]}")
}
