plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-auth:keycloak-auth-client"))
    api(project(":i2-keycloak:keycloak-f2:user:user-domain"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-roles-grant"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-roles-revoke"))

    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

    testImplementation(project(":i2-test:test-bdd"))
}
