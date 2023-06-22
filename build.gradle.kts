plugins {
	kotlin("jvm") version "1.8.22"
	id("org.graalvm.buildtools.native") version "0.9.23"
}

group = "ir.mmd.linux.utility"
version = "1.0.0"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(17)
}
