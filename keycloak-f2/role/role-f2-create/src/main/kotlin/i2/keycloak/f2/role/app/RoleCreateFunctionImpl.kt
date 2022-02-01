package i2.keycloak.f2.role.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.f2.role.domain.features.command.RoleCreateCommand
import i2.keycloak.f2.role.domain.features.command.RoleCreateFunction
import i2.keycloak.f2.role.domain.features.command.RoleCreatedResult
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleCreateFunctionImpl {

	@Bean
	fun roleCreateFunction(): RoleCreateFunction = keycloakF2Function { cmd, client ->
		client.initRole(cmd)
		RoleCreatedResult(cmd.name)
	}

	private fun AuthRealmClient.initRole(cmd: RoleCreateCommand) {
		val compositeRoles = cmd.composites.map { role ->
			getRoleResource(cmd.realmId, role).toRepresentation()
		}
		createRole(cmd.realmId, cmd.name, cmd.description, cmd.isClientRole)
		getRoleResource(cmd.realmId, cmd.name).addComposites(compositeRoles)
	}

	private fun AuthRealmClient.createRole(realmId: RealmId, role: RoleName, description: String?, isClientRole: Boolean): RoleRepresentation {
		return RoleRepresentation().apply {
			this.name = role
			this.description = description
			this.clientRole = isClientRole
		}.also { roleRepresentation ->
			roles(realmId).create(roleRepresentation)
		}
	}
}
