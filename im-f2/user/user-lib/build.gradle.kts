plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.userDomain))

    implementation(project(Modules.F2.privilegeLib))

    implementation(project(Modules.Api.config))
    implementation(project(Modules.Core.organizationApi))
    implementation(project(Modules.Core.privilegeApi))
    implementation(project(Modules.Core.userApi))

    implementation(project(Modules.Infra.keycloak))
    implementation(project(Modules.Infra.redis))
}
