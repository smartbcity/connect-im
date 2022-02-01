package i2.keycloak.f2.group.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdatedResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupUpdateFunctionImpl {

	@Bean
	fun groupUpdateFunction(): GroupUpdateFunction = keycloakF2Function { cmd, client ->
		try {
			val groupResource = client.getGroupResource(cmd.realmId, cmd.id)

			groupResource.toRepresentation().apply {
				name = cmd.name
				attributes = cmd.attributes
				realmRoles = cmd.roles
			}.let(groupResource::update)

			GroupUpdatedResult(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] wGroup[${cmd.id}] Error updating",
				payload = emptyMap()
			).asI2Exception(e)
		}
		GroupUpdatedResult(cmd.id)
	}
}
