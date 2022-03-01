package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.query.UserGetRolesQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetRolesQueryResult
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetRolesQueryFunctionImpl {

	@Bean
	fun userGetRolesQueryFunctionImpl(): UserGetRolesQueryFunction = keycloakF2Function { cmd, realmClient ->
		realmClient.keycloak.realm(cmd.realmId).users().get(cmd.userId).roles().realmLevel().listEffective()
			.map(RoleRepresentation::getName)
			.asResult()
	}

	private fun List<String>.asResult(): UserGetRolesQueryResult {
		return UserGetRolesQueryResult(
			roles = this
		)
	}
}
