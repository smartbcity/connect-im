plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.Infra.keycloak))

    api(project(Modules.imCommonsApi))

    api(project(Modules.Script.config))
    api(project(Modules.Script.functionCore))

    implementation(project(Modules.Core.clientApi))

    api(project(Modules.imKeycloakF2RealmDomain))
    implementation(project(Modules.imKeycloakF2RealmCommand))
    implementation(project(Modules.imKeycloakF2RealmQuery))

    api(project(Modules.imKeycloakF2RoleDomain))
    implementation(project(Modules.imKeycloakF2RoleCommand))
    implementation(project(Modules.imKeycloakF2RoleQuery))

    api(project(Modules.imKeycloakF2UserDomain))
    implementation(project(Modules.imKeycloakF2UserCommand))
    implementation(project(Modules.imKeycloakF2UserQuery))
}
