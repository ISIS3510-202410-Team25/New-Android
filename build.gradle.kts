// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
//    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false

    // Dagger - Hilt
    id("com.google.dagger.hilt.android") version "2.44" apply false
    kotlin("kapt") version "1.8.20"

    // Google Services
    id("com.google.gms.google-services") version "4.4.1" apply false
}