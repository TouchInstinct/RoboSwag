plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.0")

    implementation("com.android.tools.build:gradle-api:4.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.61")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")

}
