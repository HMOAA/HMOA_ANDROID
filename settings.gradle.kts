pluginManagement {
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

rootProject.name = "Hmoa"
include(":app")
include(":core-model")
include(":core-network")
include(":core-datastore")
include(":core-designsystem")
include(":sample-app")
include(":core-repository")
include(":core-network")
include(":authentication")
include(":feature-userinfo")
include(":userInfo")
