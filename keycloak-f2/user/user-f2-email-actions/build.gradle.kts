plugins {
	id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":i2-keycloak:keycloak-f2:user:user-domain"))
    api(project(":i2-keycloak:keycloak-f2:commons:commons-api"))
    api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
}
