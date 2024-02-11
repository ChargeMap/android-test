plugins {
    id("acafsxb.feature")
    id("acafsxb.library.compose")
}

android {
    namespace = "com.shindra.acafsxb.feature.balance"
}

dependencies {
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
}