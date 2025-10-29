plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
sourceSets {
    test {
        kotlin.srcDirs("src/test")
    }
}
dependencies {
    implementation(project(":core_domain"))
    implementation("io.github.g0dkar:qrcode-kotlin:4.5.0")
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))
}
