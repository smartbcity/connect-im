plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:role:role-domain"))

    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))

    implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

    testImplementation(project(":i2-test:test-bdd"))
}
