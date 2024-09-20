pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.sayandev.org/snapshots")
    }
}

plugins {
    id("org.sayandev.stickynote.settings") version "1.7.54"
}
rootProject.name = "ThePit"
