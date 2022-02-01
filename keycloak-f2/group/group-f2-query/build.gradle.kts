plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:group:group-domain"))
    api(project(":i2-keycloak:keycloak-f2:commons:commons-api"))

    implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
}
