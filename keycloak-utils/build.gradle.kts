plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":i2-s2:error:error-domain"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")
}
