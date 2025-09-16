pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/jcenter")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        // React Native
        includeBuild("../node_modules/@react-native/gradle-plugin")
    }
}

// React Native
plugins { id("com.facebook.react.settings") }
extensions.configure<com.facebook.react.ReactSettingsExtension> { autolinkLibrariesFromCommand() }

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/jcenter")
        maven("https://maven.aliyun.com/repository/google")
        google()
        mavenCentral()
    }
}

rootProject.name = "DarcySkipAds"
include(":app")
include(":lib_access_skip")
include(":lib_overlay")

// React Native
includeBuild("../node_modules/@react-native/gradle-plugin")
