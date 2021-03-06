import extensions.implementation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = ApplicationConfiguration.groupId
version = ApplicationConfiguration.version

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(ApplicationDependencies.GradlePlugins.kotlinGradlePlugin)
    }
}

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
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

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = ApplicationConfiguration.artifactId
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(ApplicationConfiguration.name)
                description.set(ApplicationConfiguration.description)
                url.set(ApplicationConfiguration.url)
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/gilbertparreno/arithmetic.git")
                    developerConnection.set("scm:git:git@github.com:gilbertparreno/arithmetic.git")
                    url.set("https://github.com/gilbertparreno/arithmetic")
                }
                developers {
                    developer {
                        id.set("gilbertparreno")
                        name.set("Gilbert Parre??o")
                        email.set("gilbertparreno95@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val repositoryUrl = if (version.toString().endsWith("SNAPSHOT")) {
                ApplicationConfiguration.snapshotRepositoryUrl
            } else {
                ApplicationConfiguration.releaseRepositoryUrl
            }
            url = uri(repositoryUrl)
            credentials {
                username = rootProject.findProperty("ossrhUsername") as? String
                password = rootProject.findProperty("ossrhPassword") as? String
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}