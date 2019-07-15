import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
}

val versions: Map<String, *> by rootProject.extra
android {
    compileSdkVersion(versions["compileSdk"] as Int)

    defaultConfig {
        minSdkVersion(16)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":utils"))
    api(project(":logging"))

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:${versions["appcompat"]}")

}
