plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(Modules.Keycloak.pluginDomain))
    Dependencies.Jvm.ktor(::implementation)
}
