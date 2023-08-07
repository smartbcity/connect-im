plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":im-keycloak:keycloak-f2:commons:im-commons-domain"))

    api("org.keycloak:keycloak-admin-client:${Versions.keycloak}")
}
