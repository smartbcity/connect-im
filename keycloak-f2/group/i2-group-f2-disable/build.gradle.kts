plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:group:i2-group-domain"))
    api(project(":i2-keycloak:keycloak-f2:commons:i2-commons-api"))

    implementation(project(":i2-keycloak:keycloak-f2:group:i2-group-f2-set-attributes"))
    implementation(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-command"))
    implementation(project(":i2-keycloak:keycloak-f2:user:i2-user-f2-query"))
}
