plugins {
    id("acafsxb.library")
    id("acafsxb.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.shindra.acafsxb.core.data"
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

}