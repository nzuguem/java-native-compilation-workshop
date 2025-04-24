import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.aot.ProcessAot

fun Project.nativeExecutableLinkedMode(): String? {
	return findProperty("nativeExecutableLinkedMode") as? String
}

plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.graalvm.buildtools.native") version "0.10.6"
}

group = "me.nzuguem"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_24
	targetCompatibility = JavaVersion.VERSION_24
}

repositories {
	mavenCentral()
}

dependencies {
	
	implementation("io.micrometer:micrometer-tracing-bridge-otel")
	implementation("io.opentelemetry:opentelemetry-exporter-otlp")
	runtimeOnly("io.micrometer:micrometer-registry-otlp")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ProcessAot> {
    args("--spring.profiles.active=" + (project.properties["aotProfiles"] ?: "default"))
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
	buildpacks = listOf(
		"docker.io/paketobuildpacks/oracle",
		"urn:cnb:builder:paketo-buildpacks/java-native-image")
}

graalvmNative {
	binaries {
		configureEach {
			buildArgs.add("--enable-sbom=classpath,export")
			buildArgs.add("--emit=build-report")

			when (project.nativeExecutableLinkedMode()) {
				"mostly" -> buildArgs.add("--static-nolibc")
				"static" -> {
					buildArgs.add("--static")
					buildArgs.add("--libc=musl")
				}
			}
		}
	}
}
