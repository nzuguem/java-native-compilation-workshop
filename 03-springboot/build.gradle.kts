import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

fun Project.nativeExecutableLinkedMode(): String? {
	return findProperty("nativeExecutableLinkedMode") as? String
}

plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.10.3"
}

group = "me.nzuguem"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_23
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
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
			buildArgs.add("-Ob")

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
