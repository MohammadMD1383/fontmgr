import org.gradle.internal.jvm.Jvm

plugins {
	java
	kotlin("jvm") version "1.8.22"
	id("org.graalvm.buildtools.native") version "0.9.23"
}

group = "ir.mmd.linux.utility"
version = "1.0.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
	implementation("io.ktor:ktor-client-core:2.3.1")
	implementation("io.ktor:ktor-client-cio:2.3.1")
	implementation("io.ktor:ktor-client-cio-jvm:2.3.1")
	
	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
	jvmToolchain(17)
}

graalvmNative {
	binaries {
		named("main") {
			javaLauncher.set(javaToolchains.launcherFor {
				languageVersion.set(JavaLanguageVersion.of(17))
				vendor.set(JvmVendorSpec.matching("Oracle Corporation"))
			})
			
			mainClass.set("ir.mmd.linux.utility.font.MainKt")
			buildArgs("-R:-InstallSegfaultHandler", "--features=ir.mmd.linux.utility.font.vm.JniClassesFeature")
		}
	}
}

val generateJNIHeaders = task<Exec>("generateJNIHeaders") {
	outputs.upToDateWhen { false }
	
	val javaFiles = sourceSets.main.get().java.filter {
		"ir/mmd/linux/utility/font/n" in it.path
	}
	
	val javac = Jvm.current().javaHome.resolve("bin/javac")
	
	commandLine(javac, "-h", "native/include", *javaFiles.toList().toTypedArray())
	
	doLast {
		javaFiles.forEach {
			val classFile = it.toPath().parent.resolve("${it.nameWithoutExtension}.class")
			classFile.toFile().delete()
		}
	}
}

val generateCmakeReleaseBuild = task<Exec>("generateCmakeBuild") {
	outputs.upToDateWhen { false }
	
	dependsOn(generateJNIHeaders)
	
	val releaseBuildDir = projectDir.resolve("native/cmake-build-release")
	releaseBuildDir.mkdirs()
	outputs.dir(releaseBuildDir)
	
	workingDir(releaseBuildDir)
	commandLine("cmake", "-DCMAKE_BUILD_TYPE=Release", "..")
}

val nativeLibsRelease = task<Exec>("nativeLibsRelease") {
	outputs.upToDateWhen { false }
	
	dependsOn(generateCmakeReleaseBuild)
	
	val releaseBuildDir = generateCmakeReleaseBuild.outputs.files.singleFile
	workingDir(releaseBuildDir)
	commandLine("make")
	
	outputs.file(releaseBuildDir.resolve("libnative.so"))
}

val copyNativeLibsReleaseForNativeImage = task<Copy>("copyNativeLibsReleaseForNativeImage") {
	outputs.upToDateWhen { false }
	
	dependsOn(nativeLibsRelease)
	
	from(nativeLibsRelease.outputs.files.singleFile)
	into(tasks.nativeCompile.get().outputs.files.singleFile)
}

tasks {
	compileJava {
		dependsOn(nativeLibsRelease)
	}
	
	nativeCompile {
		finalizedBy(copyNativeLibsReleaseForNativeImage)
	}
}
