plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))
    api(project(":im-role:role-domain"))

    implementation(project(Modules.imKeycloakF2RoleCommand))
}
