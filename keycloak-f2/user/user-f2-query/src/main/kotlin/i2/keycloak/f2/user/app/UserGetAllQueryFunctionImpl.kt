package i2.keycloak.f2.user.app

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.app.model.asModels
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetAllQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetAllQueryResult
import i2.keycloak.realm.client.config.AuthRealmClient
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetAllQueryFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	@Bean
	fun userGetAllQueryFunctionImpl(): UserGetAllQueryFunction = keycloakF2Function { cmd, realmClient ->
		val usersRepresentation = if (cmd.groupId == null) {
			getAllUsers(realmClient, cmd.realmId)
		} else {
			getUserOfGroup(realmClient, cmd.realmId, cmd.groupId!!)
		}

		var users = usersRepresentation.asModels { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }

		cmd.email?.let {
			users = users.filter { user -> if (user.email == null) false else user.email!!.contains(it) }
		}

		cmd.role?.let {
			users = users.filter { user -> user.realmRoles.contains(it) }
		}

		val count = users.count()
		if (cmd.page.size != null && cmd.page.page != null) {
			users = users.chunked(cmd.page.size!!)[cmd.page.page!!]
		}

		UserGetAllQueryResult(
			Page(
				total = count,
				list = users
			)
		)
	}

	private fun getAllUsers(client: AuthRealmClient, realmId: String): List<UserRepresentation> {
		return client.keycloak.realm(realmId).users().list()
	}

	private fun getUserOfGroup(client: AuthRealmClient, realmId: String, groupId: GroupId): List<UserRepresentation> {
		return client.getGroupResource(realmId, groupId).members()
	}
}
