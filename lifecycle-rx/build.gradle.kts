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
    api(project(":utils"))
    api(project(":logging"))
    api(project(":lifecycle"))

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:${versions["appcompat"]}")
    implementation("androidx.lifecycle:lifecycle-extensions:${versions["lifecycle"]}")
    implementation("io.reactivex.rxjava2:rxjava:${versions["rxJava"]}")
    implementation("io.reactivex.rxjava2:rxandroid:${versions["rxAndroid"]}")
}
