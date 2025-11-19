plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.devtools.ksp)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}
dependencies{
    implementation(project(":core:logger"))
    
    implementation(libs.kotlinx.coroutines.core)
    //dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}
