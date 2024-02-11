pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AcafSxb"
include(":app")
include(":core:designsystem")
include(":core:network")
include(":core:data")
include(":core:model")
include(":feature:planes")
include(":core:domain")
include(":feature:planeDetails")
