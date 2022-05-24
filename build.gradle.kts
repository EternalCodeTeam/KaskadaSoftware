import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}


group = "com.eternalcode"
version = "1.0.0"

repositories {
    gradlePluginPortal()
    mavenCentral()

    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.Minestom:Minestom:f5dc048185")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

