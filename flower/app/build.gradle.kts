plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.flower"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flower"
        minSdk = 28
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
    implementation(libs.firebase.messaging)
    val fragment_version = "1.6.2"
    val retrofit = "2.9.0"



    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.fragment:fragment:$fragment_version")

    implementation("com.squareup.retrofit2:retrofit:$retrofit'")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")


    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}