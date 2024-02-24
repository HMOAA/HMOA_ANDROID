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
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
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
include(":feature-userInfo")
include(":feature-authentication")
include(":mylibrary")
include(":core-database")
