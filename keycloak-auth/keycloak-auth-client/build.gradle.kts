plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-domain"))
    api(project(":i2-keycloak:keycloak-utils"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")

}

apply(from = rootProject.file("gradle/publishing.gradle"))