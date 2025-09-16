// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    // React Native
    id ("com.facebook.react.rootproject")
}

buildscript {
    dependencies {
        // React Native
        classpath("com.facebook.react:react-native-gradle-plugin")
    }
}