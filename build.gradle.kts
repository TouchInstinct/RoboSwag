import org.jetbrains.kotlin.config.KotlinCompilerVersion

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.2")
        classpath(kotlin("gradle-plugin", version = KotlinCompilerVersion.VERSION))
        classpath("de.aaschmid:gradle-cpd-plugin:1.1")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.0.0-RC12")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("http://dl.bintray.com/touchin/touchin-tools")
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}

extra.set(
        "versions",
        mapOf(
                "compileSdk" to 28,
                "appcompat" to "1.0.2",
                "androidx" to "1.0.0",
                "androidxKtx" to "1.0.1",
                "material" to "1.0.0",
                "lifecycle" to "2.0.0",
                "dagger" to "2.17",
                "room" to "2.0.0",
                "paging" to "2.1.0",
                "retrofit" to "2.4.0",
                "rxJava" to "2.2.2",
                "rxAndroid" to "2.1.1",
                "glide" to "4.8.0",
                "googleMaps" to "16.1.0",
                "location" to "16.0.0",
                "crashlytics" to "2.9.9"
        )
)

