plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:group:group-domain"))
    api(project(":i2-keycloak:keycloak-f2:commons:commons-api"))

    implementation(project(":i2-keycloak:keycloak-f2:group:group-f2-set-attributes"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-disable"))
    implementation(project(":i2-keycloak:keycloak-f2:user:user-f2-query"))
}
