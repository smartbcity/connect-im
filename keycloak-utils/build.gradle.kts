plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:commons:commons-domain"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")
}
