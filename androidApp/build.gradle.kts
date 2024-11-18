plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.jooheon.dday.android"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()
    defaultConfig {
        applicationId = "com.jooheon.dday.android"

        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.compile.get().toInt()

        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.androidWidget)

    implementation(libs.androidx.activity.compose)
    implementation(libs.coil.compose)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    androidTestDebugImplementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.ui.tooling.preview.build)
    implementation(libs.androidx.compose.navigation)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // hilt-navigation
    implementation(libs.hilt.androidx.navigation.compose)

    implementation(libs.compose.emoji.picker)
}