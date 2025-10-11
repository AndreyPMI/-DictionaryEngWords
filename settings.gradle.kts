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

include(":core:navigation")
include(":core:ui")
include(":core_domain")
include(":core:navigation_api")
include(":core:logger")

include(":feature:cards")
include(":feature:word_list")
include(":feature:user_feature")
