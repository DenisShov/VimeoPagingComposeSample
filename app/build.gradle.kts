plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.dshovhenia.compose.playgroundapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dshovhenia.compose.playgroundapp"
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
        viewBinding = true
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    val kotlin_version = "1.8.10"
    val retrofit_version = "2.6.0"
    val android_activity = "1.7.2"
    val android_fragment = "1.6.1"
    val nav_version = "2.7.3"
    val lifecycle_version = "2.6.2"
    val paging_version = "3.2.1"
    val room_version = "2.3.0-alpha02"

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.activity:activity-ktx:$android_activity")
    implementation("androidx.fragment:fragment-ktx:$android_fragment")
    implementation("com.google.android.material:material:1.9.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

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
    kapt("androidx.room:room-compiler:$room_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:3.10.0")

    // Stetho
    implementation("com.facebook.stetho:stetho-okhttp3:1.5.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.github.bumptech.glide:recyclerview-integration:4.12.0") {
        // Excludes the support library because it's already included by Glide.
        isTransitive = false
    }

    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}