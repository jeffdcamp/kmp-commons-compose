@file:OptIn(ExperimentalKotlinGradlePluginApi::class)
import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kover)
    alias(libs.plugins.download)
    alias(libs.plugins.detekt)
    alias(libs.plugins.vanniktechPublishing)
}

kotlin {
    applyDefaultHierarchyTemplate()

    compilerOptions {
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
        optIn.add("kotlin.time.ExperimentalTime")
    }

    android {
        namespace = "org.dbtools.kmp.commons.compose"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        // Enable this if there are any Android resource files
        // androidResources.enable = true

        // Host-side (JVM) unit tests for androidMain code (no device required).
        withHostTestBuilder {
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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
    val appleTargets = listOf(
        iosArm64(),
        iosSimulatorArm64(),
//        macosArm64(),
    )
    // Klibs cross-compile on any host (used for maven publishing), but linking an Apple framework
    // binary requires a macOS host. Only declare the frameworks on Mac so Linux CI can still
    // assemble/publish the klibs without failing on the framework link tasks.
    if (HostManager.hostIsMac) {
        appleTargets.forEach {
            it.binaries.framework {
                baseName = "KMPCommonsCompose"
                val version: String by project
                binaryOption("bundleVersion", version)
            }
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
                implementation(libs.dbtools.kmp.commons)
                implementation(libs.ktor.http)
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
                implementation(libs.assertk)
            }
        }
    }
}

// ===== Detekt =====
// download detekt config file
tasks.register<Download>("downloadDetektConfig") {
    download {
        onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
        src("https://mobile-cdn.churchofjesuschrist.org/android/build/detekt/v2/detektConfig-latest.yml")
        dest("$projectDir/build/config/detektConfig.yml")
    }
}

// ./gradlew detekt
detekt {
    source.setFrom("src/commonMain/kotlin", "src/androidMain/kotlin", "src/iosMain/kotlin")
    allRules = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
    config.setFrom(files("$projectDir/build/config/detektConfig.yml")) // point to your custom config defining rules to run, overwriting default behavior
    // baseline = file("$projectDir/config/detektBaseline.xml") // a way of suppressing issues before introducing detekt
}

tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
    dependsOn("downloadDetektConfig")

    // ignore ImageVector files
    exclude("**/ui/compose/icons/**")
    exclude("**/icons/**")

    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
    }
}

// ./gradlew koverHtmlReport
// ./gradlew koverVerify
kover {
    reports {
        verify {
            rule {
                minBound(0)
            }
        }
    }
}

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
