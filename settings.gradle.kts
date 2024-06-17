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
include(":feature-authentication")
include(":mylibrary")
include(":core-database")
include(":core-domain")
include(":feature-userInfo")
include(":core-common")
include(":feature-main")
include(":feature-perfume")
include(":feature-community")
include(":feature-hpedia")
include(":feature-home")
include(":feature-brand")
include(":feature-like")
include(":feature-magazine")
