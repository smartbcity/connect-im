package i2.keycloak.f2.group.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupCreatedResult
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.realmsResource
import i2.keycloak.utils.isFailure
import i2.keycloak.utils.onCreationFailure
import i2.keycloak.utils.toEntityCreatedId
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupCreateFunctionImpl {

	@Bean
	fun groupCreateFunction(): GroupCreateFunction = keycloakF2Function { cmd, client ->
		client.createGroup(cmd).let(::GroupCreatedResult)
	}

	private fun AuthRealmClient.createGroup(cmd: GroupCreateCommand): GroupId {
		val response = GroupRepresentation().apply {
			name = cmd.name
			attributes = cmd.attributes
			realmRoles = cmd.roles
		}.let(realmsResource().realm(cmd.realmId).groups()::add)

		if (response.isFailure()) {
			response.onCreationFailure("group")
		}
		return response.toEntityCreatedId()
	}
}
