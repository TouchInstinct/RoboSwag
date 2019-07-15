plugins {
    id("com.android.library")
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

    implementation("androidx.core:core:${versions["androidx"]}")
    implementation("androidx.annotation:annotation:${versions["androidx"]}")

    implementation("io.reactivex.rxjava2:rxjava:${versions["rxJava"]}")
    implementation("io.reactivex.rxjava2:rxandroid:${versions["rxAndroid"]}")
}
