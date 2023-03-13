import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
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
    // flexmark
    implementation("com.vladsch.flexmark:flexmark-all:0.64.0")// markdown
    implementation("com.vladsch.flexmark:flexmark-ext-gitlab:0.64.0")// mermaid markdown
    implementation("org.jsoup:jsoup:1.15.3")// transitive vulnerability fix

    // kotlinx-html-jvm
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")// HTML DSL

    // javalin
    implementation("io.javalin:javalin:4.6.7")// HTTP Server
    implementation("org.slf4j:slf4j-simple:2.0.6")// required by javalin

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    explicitApi()
    jvmToolchain(8)
}

java {
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}

detekt {
    config = files("detekt-overrides.yml")
    buildUponDefaultConfig = true
}

publishing {
    publications {
        create<MavenPublication>("web-libk") {
            from(components["java"])
        }
    }
}
