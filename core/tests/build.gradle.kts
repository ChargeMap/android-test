plugins {
    id("acafsxb.library")
}

android {
    namespace = "com.shindra.chargemap.core.tests"
}

dependencies {

    api(libs.junit4)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)

    api(libs.junit)
    api(libs.androidx.test.espresso.core)
    api(libs.mockk.instrumental)
    api(libs.kotlinx.coroutines.test)
}