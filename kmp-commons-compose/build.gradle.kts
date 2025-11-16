@file:OptIn(ExperimentalKotlinGradlePluginApi::class)
import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.kover)
    alias(libs.plugins.download)
    alias(libs.plugins.vanniktechPublishing)
}

kotlin {
    applyDefaultHierarchyTemplate()

    compilerOptions {
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
        optIn.add("kotlin.time.ExperimentalTime")
    }

    androidLibrary {
        namespace = "org.dbtools.kmp.commons.compose"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget.set(
                    JvmTarget.JVM_17
                )
            }
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

//    linuxX64()

//    js {
//        browser()
//        nodejs()
//    }

    // Mac / iOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
//        macosX64(),
//        macosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "KMPCommonsCompose"
            val version: String by project
            binaryOption("bundleVersion", version)
        }
    }

    // ==== currently unsupported ====
//    macosArm64()
//    iosX64()
//    iosArm64()
//    iosSimulatorArm64()
//    watchosArm32()
//    watchosArm64()
//    watchosSimulatorArm64()
//    watchosDeviceArm64()
//    watchosX64()
//    tvosArm64()
//    tvosSimulatorArm64()
//    tvosX64()

//    mingwX64()
//    linuxArm64()

//    androidNativeArm32()
//    androidNativeArm64()
//    androidNativeX86()
//    androidNativeX64()


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.atomicfu)
                implementation(libs.kotlin.coroutines.core)
                implementation(libs.kotlin.datetime)
                implementation(libs.kermit)
                implementation(libs.jetbrains.compose.runtime)
                implementation(libs.jetbrains.compose.material3)
                implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
                implementation(libs.jetbrains.material.icons)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.assertK)
            }
        }
    }
}

// ./gradlew koverHtmlReport
// ./gradlew koverVerify
//kover {
//    reports {
//        verify {
//            rule {
//                minBound(0)
//            }
//        }
//    }
//}

// ./gradlew clean build check publishToMavenLocal
// ./gradlew clean build check publishToMavenCentral
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    configure(
        com.vanniktech.maven.publish.KotlinMultiplatform(
            javadocJar = com.vanniktech.maven.publish.JavadocJar.Empty(),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release"),
        )
    )
}
