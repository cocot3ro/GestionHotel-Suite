plugins {
    application
    kotlin("jvm") version "2.0.20"
}

group = "com.cocot3ro.tools"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(libs.okhttp)
    implementation(libs.gson)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass.set("com.cocot3ro.tools.MainKt")
}