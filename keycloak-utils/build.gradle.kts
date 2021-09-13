plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies {
    api(project(":i2-s2:error:error-domain"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")
}

apply(from = rootProject.file("gradle/publishing.gradle"))
