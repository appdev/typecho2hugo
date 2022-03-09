plugins {
    kotlin("jvm") version "1.6.10"
    java
}

group = "com.apkdv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo1.maven.org/maven2/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.exposed", "exposed-core", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.37.3")
    implementation("org.xerial:sqlite-jdbc:3.36.0.1")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("org.slf4j:slf4j-simple:1.8.0-beta4")
    implementation("org.commonmark:commonmark:0.18.2")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.google.code.gson:gson:2.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}