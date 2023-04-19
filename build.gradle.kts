buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
    id("com.android.library") version "7.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    jvm("desktop")
    android()
    js(IR) {
        browser()
        binaries.executable()
    }

    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-framework",
                    "CoreFoundation",
                    "-linker-option",
                    "Metal"
                )
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal"
                )
            }
        }
    }
    iosX64("uikitX64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }
    iosArm64("uikitArm64") {
        binaries {
            executable() {
                entryPoint = "main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
                // TODO: the current compose binary surprises LLVM, so disable checks for now.
                freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(compose.materialIconsExtended)
                implementation(compose.material3)
                // implementation(Odyssey.core)
                // implementation(Odyssey.compose)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.squareup.okhttp3:okhttp:4.10.0")
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.9.0")
                implementation("androidx.activity:activity-compose:1.7.0")
                implementation("com.squareup.okhttp3:okhttp:4.10.0")
                implementation("com.google.accompanist:accompanist-insets:0.21.0-beta")
            }
        }

        val jsMain by getting {
            dependencies {
                api("androidx.core:core-ktx:1.9.0")
                implementation("io.ktor:ktor-client-js:2.2.4")
                implementation(npm("uuid", "8.3.2"))
            }
        }

        val iosMain by creating {
            // dependsOn(commonMain)
        }
        val macosMain by creating {
            dependsOn(iosMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
        val uikitMain by creating {
            dependsOn(iosMain)
        }
        val uikitX64Main by getting {
            dependsOn(uikitMain)
        }
        val uikitArm64Main by getting {
            dependsOn(uikitMain)
        }
    }
}

android {
    compileSdkVersion(33)

    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(33)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        pickFirst("lib/x86_64/libjsc.so")
        pickFirst("lib/arm64-v8a/libjsc.so")
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
            assets.srcDirs("src/androidMain/assets", "src/commonMain/assets")
        }
    }
    buildToolsVersion = "30.0.3"
    buildFeatures {
        viewBinding = true
    }
    /*
    testOptions {
        unitTests {
            all {
                // Workaround for Java 9+ compatibility issue with ASM library
                jvmArgs '-noverify'
            }
        }
    }

     */
}

tasks.withType<Test> {
    useJUnitPlatform()
    //maxParallelForks = Runtime.getRuntime().availableProcessors()
}

compose.desktop {
    application {
        mainClass = "Main_desktopKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "StudChat"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "Compose Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
        }
    }
}

compose.experimental {
    web.application {}
    uikit.application {
        bundleIdPrefix = "ru.agladkov"
        projectName = "StudChat"
        deployConfigurations {
            simulator("IPhone13") {
                //Usage: ./gradlew iosDeployIPhone13Debug
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_PRO
            }
            simulator("IPadUI") {
                //Usage: ./gradlew iosDeployIPadUIDebug
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPAD_MINI_6th_Gen
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}

compose.desktop.nativeApplication {
    targets(kotlin.targets.getByName("macosX64"))
    distributions {
        targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg)
        packageName = "StudChat"
        packageVersion = "1.0.0"
    }
}

// a temporary workaround for a bug in jsRun invocation - see https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
        nodeVersion = "16.0.0"
    }
}


// TODO: remove when https://youtrack.jetbrains.com/issue/KT-50778 fixed
project.tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile::class.java).configureEach {
    kotlinOptions.freeCompilerArgs += listOf(
        "-Xir-dce-runtime-diagnostic=log"
    )
}

task("generateResources") {
    DrawableCodeGenerator.generate(
        directoryPointer = "src",
        projectDir = projectDir
    )
}

object DrawableCodeGenerator {
    fun generate(
        directoryPointer: String,
        projectDir: File
    ) {
        val drawableDir = File(projectDir.absolutePath, "src/commonMain/resources/drawable")
        val drawableFiles = drawableDir.listFiles()!!.sorted()
        check(drawableFiles)

        drawableFiles
            .map { "expect val drawable_${it.nameWithoutExtension}: Any" }
            .let { listOf("package generated\n") + it }
            .writeToFile(projectDir, directoryPointer, "commonMain")

        drawableFiles
            .map { "actual val drawable_${it.nameWithoutExtension} = \"drawable/${it.name}\" as Any" }
            .let { listOf("package generated\n") + it }
            .writeToFile(projectDir, directoryPointer, "desktopMain")

        drawableFiles
            .map { "actual val drawable_${it.nameWithoutExtension} = \"drawable/${it.name}\" as Any" }
            .let { listOf("package generated\n") + it }
            .writeToFile(projectDir, directoryPointer, "jsMain")

        drawableFiles
            .map { "actual val drawable_${it.nameWithoutExtension} = \"drawable/${it.name}\" as Any" }
            .let { listOf("package generated\n") + it }
            .writeToFile(projectDir, directoryPointer, "macosMain")

        drawableFiles
            .map { "actual val drawable_${it.nameWithoutExtension} = \"drawable/${it.name}\" as Any" }
            .let { listOf("package generated\n") + it }
            .writeToFile(projectDir, directoryPointer, "uikitMain")

        drawableFiles
            .map { "actual val drawable_${it.nameWithoutExtension} = R.drawable.${it.nameWithoutExtension} as Any" }
            .let { listOf("package generated", "import com.example.studchat.R\n") + it }
            .writeToFile(projectDir, directoryPointer, "androidMain")

    }

    private fun check(drawableFiles: List<File>) {
        val names = drawableFiles.map { it.nameWithoutExtension }
        val duplicates = names.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        if (duplicates.isNotEmpty()) {
            throw Exception("drawables can't have same names due android limitations: $duplicates")
        }

        val haveUpperCase = names.filter { it != it.toLowerCase() }
        if (haveUpperCase.isNotEmpty()) {
            throw Exception("drawables can't have uppercase in names due android limitations: $haveUpperCase")
        }

    }

    private fun List<String>.writeToFile(projectDir: File, packageName: String, sourceSet: String) {
        val parts = packageName.split('.').toTypedArray()
        val file = File(projectDir.absolutePath, "${parts.joinToString("/")}/$sourceSet/kotlin/generated/drawable.kt")
        if (!file.exists()) {
            file.parentFile.mkdir()
        }
        file.writeText(joinToString(System.lineSeparator()))
    }
}

object Odyssey {
    const val core = "io.github.alexgladkov:odyssey-core:0.1.4"
    const val compose = "io.github.alexgladkov:odyssey-compose:0.1.4"
}