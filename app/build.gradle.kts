plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.appbooking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appbooking"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {


            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"

            )

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8" // Correct syntax and matches Java version
    }

    buildToolsVersion = "35.0.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo.turso.tech/maven") }
    }
    dependencies {
        // Gradle plugin compatible with compileSdk 35
        classpath("com.android.tools.build:gradle:8.5.1")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("tech.turso.libsql:libsql:0.1.0")
//    implementation ("com.github.bumptech.glide:glide:4.13.0")
//    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.0")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
    implementation ("com.google.android.gms:play-services-auth:20.4.1")// Để sử dụng xác thực Google
    implementation ("com.google.android.gms:play-services-drive:17.0.0") // Google Drive API



}


