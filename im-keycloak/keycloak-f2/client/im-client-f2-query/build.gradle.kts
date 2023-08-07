plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-keycloak:keycloak-f2:client:im-client-domain"))
    api(project(":im-keycloak:keycloak-f2:user:im-user-f2-query"))
    api(project(":im-keycloak:keycloak-f2:commons:im-commons-api"))
}
