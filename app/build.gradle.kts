plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.calgeo"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.a2dshapecalculatormobileapp"

        minSdk = 26
        targetSdk = 36

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // AndroidX Core
    implementation("androidx.core:core:1.19.0")

    // AppCompat
    implementation("androidx.appcompat:appcompat:1.8.0")

    // Material Design
    implementation("com.google.android.material:material:1.14.0")

    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.2.2")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.10.0")
    implementation("androidx.navigation:navigation-ui:2.10.0")

    // Unit Test
    testImplementation("junit:junit:4.13.2")

    // Instrumentation Test
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}