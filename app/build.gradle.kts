plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.lucasnvs.siboon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lucasnvs.siboon"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.rxjava)
    implementation(libs.adapter.rxjava3)
    implementation(libs.room.rxjava3)
    implementation(libs.rxandroid)

    implementation(libs.picasso)
    implementation(libs.retrofit2)
    implementation(libs.converter.gson)
    implementation(libs.okhttp3)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}