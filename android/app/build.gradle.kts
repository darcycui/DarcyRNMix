import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.implementation
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // React Native
    id ("com.facebook.react")
}
react {
    /* AutoLinking */
    autolinkLibrariesWithApp()
}

android {
    namespace = "com.darcy.skipads"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.darcy.skipads"
        minSdk = 26
        targetSdk = 35
        versionCode = 3
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // load key store from local.properties
    val localProperties = gradleLocalProperties(File(project.rootDir.absolutePath))
    signingConfigs {
        create("debugSign") {
            storeFile = file(localProperties.getProperty("keystore_path"))
            keyAlias = localProperties.getProperty("key_alias")
            storePassword = localProperties.getProperty("store_password")
            keyPassword = localProperties.getProperty("key_password")
        }

        create("releaseSign") {
            storeFile = file(localProperties.getProperty("keystore_path"))
            keyAlias = localProperties.getProperty("key_alias")
            storePassword = localProperties.getProperty("store_password")
            keyPassword = localProperties.getProperty("key_password")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debugSign")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("releaseSign")
        }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .matching { it.name.contains("release") }
            .configureEach {
                // 任务1: 修改APK名称
                val formatter = SimpleDateFormat("yyyyMMdd-HH-mm-ss")
                val buildTime = formatter.format(Date())
                val newOutputFileName = "DarcySkipAds_V${variant.versionName}_${buildTime}.apk"
                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = newOutputFileName

                // 任务2: 复制APK到根目录release文件夹
                val copyTask = tasks.register("copy${variant.name}Apk", Copy::class) {
                    from(outputFile)
                    into(File(project.rootDir, "release"))
                    // 添加依赖确保文件存在
                    dependsOn(variant.assembleProvider)
                }

                // 挂接到构建流程
                // 正确挂接任务（使用 finalizedBy）
                val variantNameUppercased = variant.name.replaceFirstChar {
                    if (it.isLowerCase()) {
                        it.uppercase(Locale.getDefault())
                    } else {
                        it.toString()
                    }
                }
                tasks.named("assemble$variantNameUppercased").configure {
                    finalizedBy(copyTask)
                }
            }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    // 开启viewBinding
    buildFeatures {
        viewBinding = true
    }
    splits {
        abi {
            reset() // 清除默认配置
            include("armeabi-v7a", "arm64-v8a") // 保留的架构
            isUniversalApk = false // 不生成包含所有架构的通用包
        }
    }
}

fun gradleLocalProperties(projectRootDir: File): Properties {
    val properties = Properties()
    val localProperties = File(projectRootDir, "local.properties")

    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else {
        println("Gradle local properties file not found at $localProperties")
    }
    return properties
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // 引入aar
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
//    implementation(files("libs/lib_access_skip-release.aar"))
    implementation(project(":lib_access_skip"))
    implementation(project(":lib_overlay"))

    // React Native
    implementation("com.facebook.react:react-android")
    val hermesEnabled = project.properties["hermesEnabled"]?.toString()?.toBoolean() ?: false
    if (hermesEnabled) {
        implementation("com.facebook.react:hermes-android")
    } else {
        implementation ("org.webkit:android-jsc:+")
    }
}