plugins {
    id("shindra.library")
    id("shindra.hilt")
}

android {
    namespace = "com.chargemap.core.domain"

}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))

}