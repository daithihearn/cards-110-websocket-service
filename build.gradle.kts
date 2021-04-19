import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

buildscript {
	val kotlinVersion = "1.4.10"
	val springBootVersion = "2.3.5.RELEASE"
	repositories {
		mavenLocal()
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
	}
}

plugins {
    id("org.springframework.boot") version "2.3.5.RELEASE"
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
	kotlin("plugin.spring") version "1.4.10"
}

apply(plugin = "maven")
apply(plugin = "io.spring.dependency-management")

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

repositories {
	mavenLocal()
	mavenCentral()
	maven(url = "https://jitpack.io")
	flatDir {
		dirs("libs")
	}
}

val versionFile = Properties()
versionFile.load(FileInputStream(".env"))

group = "ie.daithi.websocket"
version = "${versionFile.getProperty("WEBSOCKET_SERVICE_VERSION")}"
java.sourceCompatibility = JavaVersion.VERSION_14

description = "Websocket Service"

val springBootVersion: String = "2.3.5.RELEASE"

dependencies {

	// Internal Dependencies

	// External Dependencies

	// Kotlin dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

	// Spring dependencies
	implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-websocket:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-redis:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
	implementation("org.springframework.security:spring-security-oauth2-resource-server:5.3.3.RELEASE")
	implementation("org.springframework.security:spring-security-oauth2-jose:5.3.3.RELEASE")


	// Other
	implementation("com.auth0:java-jwt:3.10.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
	// implementation("org.apache.commons:commons-text:1.8")

	//Test
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
	testImplementation("io.mockk:mockk:1.10.2")


}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "14"
	}
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}