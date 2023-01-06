plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:client:i2-client-domain"))

    implementation(project(":i2-keycloak:keycloak-f2:commons:i2-commons-api"))

    testImplementation(project(":i2-test:test-bdd"))
}
