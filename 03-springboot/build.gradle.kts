import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

fun Project.nativeExecutableLinkedMode(): String? {
	return findProperty("nativeExecutableLinkedMode") as? String
}

plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "me.nzuguem"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
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
			buildArgs.add("--enable-sbom=cyclonedx")

			when (project.nativeExecutableLinkedMode()) {
				"mostly" -> buildArgs.add("-H:+StaticExecutableWithDynamicLibC")
				"static" -> {
					buildArgs.add("--static")
					buildArgs.add("--libc=musl")
				}
			}
		}
	}
}
