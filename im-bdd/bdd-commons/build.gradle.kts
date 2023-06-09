plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(":im-api:api-config"))
	implementation(project(":im-organization:organization-api"))
	implementation(project(":im-role:role-api"))
	implementation(project(":im-user:user-api"))
	implementation(project(":im-user:user-lib"))
	implementation(project(":im-role:role-api"))
	implementation(project(":im-commons:im-commons-domain"))

	Dependencies.Jvm.f2(::implementation)
	Dependencies.Mpp.f2(::implementation)
	Dependencies.Mpp.s2(::implementation)
	Dependencies.Jvm.cucumber(::implementation)
	Dependencies.Jvm.i2AuthClient(::implementation)

	Dependencies.Jvm.testcontainers(::testImplementation)
	Dependencies.Jvm.testcontainers(::testImplementation)

	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
}
