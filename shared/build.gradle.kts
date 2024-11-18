plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.ksp)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    jvmToolchain(17)
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xallocator=std") // https://youtrack.jetbrains.com/issue/KT-66589/iOS-widget-crashes-after-consumed-memory-reaches-30MB-because-KMM-is-not-releasing-the-memory#:~:text=iOS%20widget%20memory%20is%20limited%20at%2030MB
            }
        }
        it.binaries.framework {
            baseName = "shared"
            binaryOption("bundleId", "com.jooheon.dday.shared")
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqlDelight.android)
            implementation("androidx.datastore:datastore-preferences:1.1.1")
        }

        iosMain.dependencies {
            implementation(libs.sqlDelight.ios)
        }

        commonMain.dependencies {
            implementation(libs.sqlDelight.common)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.jooheon.dday"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("DdayDatabase") {
            packageName.set("com.jooheon.dday.data.database")
            srcDirs("src/commonMain/kotlin")
        }
    }
}