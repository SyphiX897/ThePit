pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.sayandev.org/snapshots")
        maven("https://repo.screamingsandals.org/public")
    }
}

plugins {
    id("org.sayandev.stickynote.settings") version "1.7.64"
}
rootProject.name = "ThePit"
