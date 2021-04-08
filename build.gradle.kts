import extensions.implementation
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "arithmetic"
version = "1.0"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(ApplicationDependencies.GradlePlugins.kotlinGradlePlugin)
    }
}

plugins {
    id("java")
    id("maven")
    kotlin("jvm") version ApplicationDependencyVersions.kotlin
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/babylonpartners/maven")
}

dependencies {
    implementation(ApplicationDependencies.dependencies)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}