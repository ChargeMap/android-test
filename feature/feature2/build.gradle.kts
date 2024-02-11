@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("chargemap.feature")
    id("chargemap.library.compose")
}

android {
    namespace = "com.chargemap.feature2"
}

dependencies {
}