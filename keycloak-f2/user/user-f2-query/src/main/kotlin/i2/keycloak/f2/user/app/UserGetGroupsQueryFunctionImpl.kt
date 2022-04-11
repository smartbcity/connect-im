package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetGroupsQueryResult
import i2.keycloak.f2.user.domain.model.UserGroup
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetGroupsQueryFunctionImpl {

	@Bean
	fun userGetGroupsQueryFunction(): UserGetGroupsQueryFunction = keycloakF2Function { cmd, realmClient ->
		val items = realmClient.getUserResource(cmd.realmId, cmd.userId).groups().map {
			UserGroup(it.id, it.name)
		}
		UserGetGroupsQueryResult(
			items
		)
	}
}
