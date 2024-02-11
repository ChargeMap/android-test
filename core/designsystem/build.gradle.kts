plugins {
    id("acafsxb.library")
    id("acafsxb.library.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    lint {
        checkDependencies = true
    }
    namespace = "com.shindra.acafsxb.core.designsystem"
}


dependencies {
    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    implementation(libs.coil.kt.compose)
 /*   lintPublish(project(":lint"))
    androidTestImplementation(project(":core:testing"))*/
}