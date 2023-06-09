pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:7.0.2")
            }
        }
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("multiplatform").version(kotlinVersion)

        val composeVersion = extra["compose.version"] as String
        id("org.jetbrains.compose").version(composeVersion)
        id("org.jetbrains.kotlin.android") version "1.8.0"
    }
}

rootProject.name = "studchat"