# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 保留 RNActivity 和 MainApplication
-keep class com.darcy.skipads.react_native.RNActivity { *; }
-keep class com.darcy.skipads.app.DarcySkipApp { *; }

# 保留 React Native 相关包
-keep class com.facebook.react.** { *; }
-keep interface com.facebook.react.** { *; }

# 保留 React Native 模块导出的类（如原生模块）
-keepclassmembers class * extends com.facebook.react.bridge.NativeModule {
    *;
}
