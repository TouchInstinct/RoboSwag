import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

val versions: Map<String, *> by rootProject.extra
val buildScriptsDir: String by rootProject.extra
android {
    compileSdkVersion(versions["compileSdk"] as Int)

    defaultConfig {
        applicationId = "ru.touchin.roboswag.components"
        minSdkVersion(16)
        targetSdkVersion(versions["compileSdk"] as Int)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("debug"){
            versionNameSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release"){
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    flavorDimensions("proguardSettings")

    productFlavors {
        create("noObfuscate") {
            setDimension("proguardSettings")
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "$buildScriptsDir/proguard/noObfuscate.pro"))
        }
        create("obfuscate") {
            setDimension("proguardSettings")
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "$buildScriptsDir/proguard/obfuscate.pro"))
        }
    }
}

dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:${versions["appcompat"]}")
}

apply(from = "$buildScriptsDir/gradle/staticAnalysis.gradle.kts")
