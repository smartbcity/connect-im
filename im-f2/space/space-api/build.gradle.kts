plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(Modules.F2.spaceDomain))
    api(project(Modules.F2.spaceLib))

    implementation(project(":im-api:api-config"))
    implementation(project(Modules.Infra.redis))

    implementation(project(Modules.imCommonsAuth))

}
