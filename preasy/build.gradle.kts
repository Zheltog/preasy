val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.ktor.plugin") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
}

group = "seven.belog"
version = "0.0.1"
application {
    mainClass.set("seven.belog.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktorServerCoreJvm)
    implementation(libs.ktorServerNettyJvm)
    implementation(libs.ktorServerContentNegotiation)
    implementation(libs.ktorSerializationKotlinxJson)
    implementation(libs.ktormCore)
    implementation(libs.ktormSupportPostgresql)
    implementation("org.postgresql:postgresql:42.6.0")
//    implementation(libs.postgresql)
    implementation(libs.logbackClassic)
    testImplementation(libs.ktorServerTestsJvm)
    testImplementation(libs.kotlinTestJunit)
    testImplementation(libs.mockitoKotlin)
}