package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsResult
import i2.keycloak.f2.user.domain.model.UserGroup
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetGroupsFunctionImpl {

	@Bean
	fun userGetGroupsQueryFunction(): UserGetGroupsFunction = keycloakF2Function { cmd, realmClient ->
		val items = realmClient.getUserResource(cmd.realmId, cmd.userId).groups().map {
			UserGroup(it.id, it.name, it.realmRoles)
		}
		UserGetGroupsResult(
			items
		)
	}
}
