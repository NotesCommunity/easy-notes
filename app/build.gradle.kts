plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "com.hadiyarajesh.easynotes"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.hadiyarajesh.easynotes"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

//    val properties = Properties()
//    properties.load(project.rootProject.file("local.properties").inputStream())
//
//    signingConfigs {
//        create("release") {
//            storeFile = file(properties.getProperty("store.file"))
//            storePassword = properties.getProperty("store.password")
//            keyAlias = properties.getProperty("key.alias")
//            keyPassword = properties.getProperty("key.password")
//        }
//    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"

            resValue("string", "app_name", "@string/app_name_debug")

            buildConfigField("String", "API_BASE_URL", "\"BASE_API_URL_DEV\"")
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                "\"719734510327-fkl6rvtae915bkc6j62u0d987jge5d0u.apps.googleusercontent.com\""
            )
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            resValue("string", "app_name", "@string/app_name_release")

            buildConfigField("String", "API_BASE_URL", "\"BASE_API_URL_PROD\"")
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                "\"265972715030-vvvo82gkkp610fd6d0ho41tmn5h22p2f.apps.googleusercontent.com\""
            )

//            signingConfig = signingConfigs.getByName("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = LibVersion.composeCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

object LibVersion {
    const val composeCompilerVersion = "1.3.2"
    const val roomVersion = "2.4.2"
    const val dataStoreVersion = "1.0.0"
    const val retrofitVersion = "2.9.0"
    const val moshiVersion = "1.13.0"
    const val accompanistVersion = "0.27.0"
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    val firebaseBom = platform("com.google.firebase:firebase-bom:31.0.2")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha04")
    implementation("androidx.datastore:datastore-preferences:${LibVersion.dataStoreVersion}")
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")

    implementation(firebaseBom)
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.android.gms:play-services-auth:20.4.0")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

    implementation("androidx.room:room-ktx:${LibVersion.roomVersion}")
    implementation("androidx.room:room-paging:${LibVersion.roomVersion}")
    kapt("androidx.room:room-compiler:${LibVersion.roomVersion}")

    implementation("com.squareup.retrofit2:retrofit:${LibVersion.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-moshi:${LibVersion.retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("com.squareup.moshi:moshi:${LibVersion.moshiVersion}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${LibVersion.moshiVersion}")

    implementation("com.google.accompanist:accompanist-swiperefresh:${LibVersion.accompanistVersion}")
    implementation("com.google.accompanist:accompanist-permissions:${LibVersion.accompanistVersion}")

    implementation("io.coil-kt:coil-compose:2.2.2") {
        because("We need image loading library")
    }

    implementation("io.github.hadiyarajesh.flower-retrofit:flower-retrofit:3.1.0") {
        because("We need networking and database caching")
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    // UI Tests
    androidTestImplementation(composeBom)
    implementation("androidx.compose.ui:ui")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Android Studio Preview support
    debugImplementation("androidx.compose.ui:ui-tooling")
}
