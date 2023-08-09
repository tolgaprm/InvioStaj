import java.util.Properties

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

var properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.prmto.inviostaj"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.prmto.inviostaj"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        buildConfigField("String", "TMDB_BASE_URL", "\"${properties.getProperty("TMDB_BASE_URL")}\"")
        testInstrumentationRunner = "com.prmto.inviostaj.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    //dagger - hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)

    // navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // coroutines for getting off the UI thread
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.retrofit.converter.moshi)

    // moshi for parsing the JSON format
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    kapt(libs.moshi.kotlin.codegen)

    // Splash Screen
    implementation(libs.splashscreen)

    // Coil
    implementation(libs.coil)

    // Local Unit Tests
    testImplementation(libs.junit)
    implementation(libs.test.core)
    testImplementation(libs.hamcrest)
    testImplementation(libs.core.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockk)
    testImplementation(libs.mockWebServer)
    testImplementation(libs.turbine)

    kaptAndroidTest(libs.dagger.hilt.compiler)

    // Instrumented Unit Tests
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)
}