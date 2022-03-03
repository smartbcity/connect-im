package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.query.UserGetRolesQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetRolesQueryResult
import i2.keycloak.f2.user.domain.model.UserRoles
import i2.keycloak.realm.client.config.realmsResource
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetRolesQueryFunctionImpl {

	@Bean
	fun userGetRolesQueryFunctionImpl(): UserGetRolesQueryFunction = keycloakF2Function { cmd, realmClient ->
		val userRoleResource = realmClient.getUserResource(cmd.realmId, cmd.userId).roles().realmLevel()

		val assignedRoles = userRoleResource.listAll().map(RoleRepresentation::getName)
		val effectiveRoles = userRoleResource.listEffective().map(RoleRepresentation::getName)

		UserGetRolesQueryResult(
			UserRoles(
			assignedRoles = assignedRoles,
			effectiveRoles = effectiveRoles
			)
		)
	}
}
