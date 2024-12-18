plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.courskotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.courskotlin"
        minSdk = 24
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    dependencies {

        // implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
        implementation("androidx.compose.material3:material3:1.2.0")
        implementation("androidx.navigation:navigation-compose:2.7.5")
        implementation("com.squareup.okhttp3:okhttp:+")
        implementation("com.google.code.gson:gson:+")
        implementation("io.coil-kt:coil-compose:1.3.2")
        implementation("com.google.accompanist:accompanist-swiperefresh:0.36.0")
        implementation("androidx.compose.ui:ui:1.5.0")
        implementation("androidx.compose.material3:material3:1.2.0")
        implementation("androidx.compose.ui:ui-text:1.5.0")
        implementation("androidx.compose.foundation:foundation:1.5.0")
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

    }
}