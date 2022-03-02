package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.app.model.asModels
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetByGroupIdQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByGroupIdQueryResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetByGroupIdQueryFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	@Bean
	fun userGetByGroupIdQueryFunctionImpl(): UserGetByGroupIdQueryFunction = keycloakF2Function { cmd, realmClient ->
		realmClient.getGroupResource(cmd.realmId, cmd.groupId).members()
			.asModels { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
			.let(::UserGetByGroupIdQueryResult)
	}
}
