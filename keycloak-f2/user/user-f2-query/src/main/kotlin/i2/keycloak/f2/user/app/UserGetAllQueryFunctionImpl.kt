package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.app.model.asModels
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetAllQueryResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetAllQueryFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	@Bean
	fun userGetAllQueryFunctionImpl(): UserGetAllQueryFunction = keycloakF2Function { cmd, realmClient ->
		realmClient.keycloak.realm(cmd.realmId).users().list()
			.asModels { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
			.let(::UserGetAllQueryResult)
	}
}
