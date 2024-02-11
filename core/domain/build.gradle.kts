plugins {
    id("acafsxb.library")
    id("acafsxb.hilt")
}

android {
    namespace = "com.chargemap.core.domain"

}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))

}