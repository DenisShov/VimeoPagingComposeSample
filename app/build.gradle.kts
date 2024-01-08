plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.dshovhenia.vimeo.paging.compose.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dshovhenia.vimeo.paging.compose.sample"
        minSdk = 24
        targetSdk = 34

        val vimeoClientId = "bf73e4b8611761c8a21495585adcf00a5fd8cf6a"
        val vimeoClientSecret =
            "5D/69g5MobTTBASuS3Gq4HUOpxB/vgr6v7KtBkBRjxWaPSlOJZmZbeXpT8F7kIH3J9ByYUXJq43BuDFX5IephLYzwVF6x2VLF4+ayaiChDDw5YV7aVr/Oh/zYREp2L0q"
        val vimeoOauthRedirect = "vimeobf73e4b8611761c8a21495585adcf00a5fd8cf6a://auth"

        buildConfigField("String", "CLIENT_ID", "\"${vimeoClientId}\"")
        buildConfigField("String", "CLIENT_SECRET", "\"${vimeoClientSecret}\"")
        buildConfigField("String", "OAUTH_REDIRECT", "\"${vimeoOauthRedirect}\"")

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
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    kapt {
        correctErrorTypes = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    val retrofit_version = "2.6.0"
    val nav_version = "2.7.3"
    val lifecycle_version = "2.6.2"
    val paging_version = "3.2.1"
    val room_version = "2.6.0"

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.10.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 2
    implementation("androidx.compose.material:material")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // Lifecycle utilities for Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Accompanist
    implementation("com.google.accompanist:accompanist-webview:0.32.0")
    // Coil Compose
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    implementation("androidx.navigation:navigation-compose:2.7.5")

    //ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("androidx.paging:paging-compose:$paging_version")

    // Room components
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-paging:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:3.10.0")

    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}