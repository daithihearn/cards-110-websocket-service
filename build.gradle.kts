import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	val kotlinVersion = "1.6.10"
	val springBootVersion = "2.7.7"
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
    id("org.springframework.boot") version "2.7.7"
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

apply(plugin = "maven-publish")
apply(plugin = "io.spring.dependency-management")

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

repositories {
	mavenLocal()
	mavenCentral()
	flatDir {
		dirs("libs")
	}
}

group = "ie.daithi.websocket"
java.sourceCompatibility = JavaVersion.VERSION_17

description = "Websocket Service"

val springBootVersion: String = "2.7.7"

dependencies {

	// Internal Dependencies

	// External Dependencies

	// Kotlin dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")

	// Spring dependencies
	implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-websocket:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-redis:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
	implementation("org.springframework.security:spring-security-oauth2-resource-server:5.5.1")
	implementation("org.springframework.security:spring-security-oauth2-jose:5.5.1")


	// Other
	implementation("com.auth0:java-jwt:3.19.3")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
	// implementation("org.apache.commons:commons-text:1.8")

	//Test
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
	testImplementation("io.mockk:mockk:1.12.1")


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
		jvmTarget = "17"
	}
}
