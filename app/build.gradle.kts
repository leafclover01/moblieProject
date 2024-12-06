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
    buildFeatures {
        viewBinding = true
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo.turso.tech/maven") }
    }
    dependencies {
        // Gradle plugin compatible with compileSdk 35
        classpath("com.android.tools.build:gradle:8.9.2")

    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.annotation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("tech.turso.libsql:libsql:0.1.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
//    implementation 'com.android.support:design:26.1.0'
//    implementation 'com.android.support:percent:26.1.0'
//    implementation 'com.android.support:cardview-v7:26.1.0'
}


