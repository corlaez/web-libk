plugins {
    kotlin("jvm") version "1.8.0"
    // for jitpack
    `java-library` // java
    `maven-publish`
}

group = "com.corlaez"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

publishing {
    publications {
        create<MavenPublication>("web-libk") {
            from(components["java"])
        }
    }
}
