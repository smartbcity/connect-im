subprojects {
	plugins.withType(JavaPlugin::class.java).whenPluginAdded {
		dependencies {
			val implementation by configurations
			implementation("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")
			implementation(project(":i2-keycloak:keycloak-f2:commons:i2-commons-api"))
		}
	}

	plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
		dependencies {
			val commonMainApi by configurations
			commonMainApi(project(":i2-keycloak:keycloak-f2:commons:i2-commons-domain"))
		}
	}
}
