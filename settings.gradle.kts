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

rootProject.name = "DictionaryForWords"
include(":app")
include(":data")
include(":core:ui")

include(":core:navigation")
include(":feature:cards")
include(":feature:word_list")
include(":core_domain")
include(":core:navigation_api")
