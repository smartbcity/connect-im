plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    implementation(project(":im-api:api-config"))

    implementation(project(Modules.imCommonsAuth))
    implementation(project(":im-infra:infra-redis"))

    api(project(Modules.S2.imApikeyLib))
    api(project(Modules.S2.imApikeyDomain))
    implementation(project(Modules.S2.imUserLib))
}