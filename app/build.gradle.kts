plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    compileSdk = 33
    namespace = "com.example.beginvegan"
    defaultConfig {
        applicationId = "com.example.beginvegan"
        minSdk = 29
        targetSdk =33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release"){
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit2
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.9.0")

    // ViewPager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    // okhttp-logging-interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // KAKAO MAP API
//    implementation files("libs/libDaumMapAndroid.jar")

    // KAKAO LOGIN API
    implementation ("com.kakao.sdk:v2-user:2.15.0") // 카카오 로그인

    // Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.0")

    // Loading Bar
    implementation ("com.github.ybq:Android-SpinKit:1.4.0")
    // indicator
    implementation ("me.relex:circleindicator:2.1.6")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    // GPS
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    // Circle ImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    // CircleIndicator
    implementation ("me.relex:circleindicator:1.3.2")
}