plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.andreypmi.dictionaryforwords.presentation"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}


dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:navigation_api"))
    implementation(project(":core_domain"))
    implementation(project(":core:logger"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    // ui compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.coil.compose)
    implementation(libs.androidx.compose.material.core)
    //navigation
    implementation(libs.navigation.ui)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.fragment)
    //dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
}
