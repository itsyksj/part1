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

rootProject.name = "part1"
include(":chapter2")
include(":chapter3")
include(":chapter4")
include(":chapter5")
include(":chapter6")
include(":chapter7")
include(":chapter8")
