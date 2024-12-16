plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nachtgeistw.igiari_watch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nachtgeistw.igiari_watch"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    implementation(libs.compose.foundation.v140)

    // For Wear Material Design UX guidelines and specifications
    implementation(libs.compose.material.v140)

    // For integration between Wear Compose and Androidx Navigation libraries
    implementation(libs.compose.navigation)

    // For Wear preview annotations
    implementation(libs.compose.ui.tooling)

    // NOTE: DO NOT INCLUDE a dependency on androidx.compose.material:material.
    // androidx.wear.compose:compose-material is designed as a replacement
    // not an addition to androidx.compose.material:material.
    // If there are features from that you feel are missing from
    // androidx.wear.compose:compose-material please raise a bug to let us know.
}