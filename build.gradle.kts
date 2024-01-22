plugins {
    kotlin("jvm") version "1.9.21"
}

group = "dev.georgiys"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("net.lingala.zip4j:zip4j:2.11.5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

