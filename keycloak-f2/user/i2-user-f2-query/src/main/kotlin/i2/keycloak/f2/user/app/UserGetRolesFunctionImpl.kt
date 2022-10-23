package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.query.UserGetRolesFunction
import i2.keycloak.f2.user.domain.features.query.UserGetRolesResult
import i2.keycloak.f2.user.domain.model.UserRoles
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetRolesFunctionImpl {

	@Bean
	fun userGetRolesQueryFunctionImpl(): UserGetRolesFunction = keycloakF2Function { cmd, realmClient ->
		val userRoleResource = realmClient.getUserResource(cmd.realmId, cmd.userId).roles().realmLevel()

		val assignedRoles = userRoleResource.listAll().map(RoleRepresentation::getName)
		val effectiveRoles = userRoleResource.listEffective().map(RoleRepresentation::getName)

		UserGetRolesResult(
			UserRoles(
			assignedRoles = assignedRoles,
			effectiveRoles = effectiveRoles
			)
		)
	}
}
