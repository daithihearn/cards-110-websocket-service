import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
	repositories {
		mavenLocal()
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
	}
}

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("maven-publish")
	kotlin("jvm") version "1.7.22"
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
}

version=File(".version").readText(Charsets.UTF_8)

group = "ie.daithi.websocket"
java.sourceCompatibility = JavaVersion.VERSION_17

description = "Websocket Service"

dependencies {

	// Internal Dependencies

	// External Dependencies

	// Kotlin dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")

	// Spring dependencies
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-oauth2-resource-server:6.0.2")
	implementation("org.springframework.security:spring-security-oauth2-jose:6.0.2")


	// Other
	implementation("com.auth0:java-jwt:4.3.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	// implementation("org.apache.commons:commons-text:1.8")

	//Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.4")


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

tasks.getByName<BootJar>("bootJar") {
	enabled = true
}

tasks.getByName<Jar>("jar") {
	enabled = false
}