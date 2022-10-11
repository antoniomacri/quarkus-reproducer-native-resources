plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.allopen") version "1.7.0"
    id("io.quarkus")
}

repositories {
    mavenLocal()
    mavenCentral()
}

group = "com.github.antoniomacri"
version = "1.0.0-SNAPSHOT"
description = "quarkus-reproducer-native-resources"

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")

    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("com.github.ua-parser:uap-java:1.5.3")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.quarkusBuild {
    nativeArgs {
        "additional-build-args" to "-H:IncludeResources='.*/regexes.yaml'"
//        "resources.includes" to "**/ua_parser/regexes.yaml"
//        "resources.includes" to ".*/ua_parser/regexes.yaml"
//        "resources.includes" to "regexes.yaml"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    sourceSets {
        test {
            resources {
                srcDirs += fileTree("src/test/resources")
            }
        }
    }
}
