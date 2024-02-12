plugins {
    id("shindra.library")
    id("shindra.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.shindra.chargemap.core.network"
}

dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    testImplementation(libs.ktor.client.mock)
    debugImplementation(libs.chucker.interceptor.debug)
    releaseImplementation(libs.chucker.interceptor.release)
}