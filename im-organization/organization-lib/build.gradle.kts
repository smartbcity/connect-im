plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":im-organization:organization-domain"))

    implementation(project(":im-api:api-config"))
    implementation(project(":im-user:user-lib"))
    implementation(project(":im-infra:infra-redis"))

    implementation("city.smartb.i2:i2-group-f2-query:${Versions.i2}")
    implementation("city.smartb.i2:i2-group-f2-create:${Versions.i2}")
    implementation("city.smartb.i2:i2-group-f2-delete:${Versions.i2}")
    implementation("city.smartb.i2:i2-group-f2-disable:${Versions.i2}")
    implementation("city.smartb.i2:i2-group-f2-set-attributes:${Versions.i2}")
    implementation("city.smartb.i2:i2-group-f2-update:${Versions.i2}")
}
