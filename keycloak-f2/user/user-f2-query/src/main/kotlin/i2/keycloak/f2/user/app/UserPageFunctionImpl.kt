package i2.keycloak.f2.user.app

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.app.model.asModels
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserPageFunction
import i2.keycloak.f2.user.domain.features.query.UserPageResult
import i2.keycloak.realm.client.config.AuthRealmClient
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserPageFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	@Bean
	fun userPageFunctionImpl(): UserPageFunction = keycloakF2Function { cmd, realmClient ->
		val userRepresentations = if (cmd.groupId == null) {
			listUsers(realmClient, cmd.realmId)
		} else {
			listUsersOfGroup(realmClient, cmd.realmId, cmd.groupId!!)
		}

		var users = userRepresentations.asModels { userId ->
			userFinderService.getRoles(userId, cmd.realmId, cmd.auth)
		}.asSequence()

		cmd.search?.split(" ")
			?.map(String::trim)
			?.forEach { searchWord ->
				users = users.filter { user ->
					user.email.orEmpty().contains(searchWord, true) ||
							user.firstName.orEmpty().contains(searchWord, true) ||
							user.lastName.orEmpty().contains(searchWord, true)
				}
			}

		cmd.role?.let { roleFilter ->
			users = users.filter { user -> roleFilter in user.roles.assignedRoles }
		}

		cmd.attributes.forEach { (key, value) ->
			users = users.filter { user -> value == user.attributes[key] }
		}

		val count = users.count()
		val usersPage = if (cmd.page.size != null && cmd.page.page != null) {
			users.chunked(cmd.page.size!!).elementAtOrNull(cmd.page.page!!).orEmpty()
		} else {
			users.toList()
		}

		UserPageResult(
			Page(
				total = count,
				items = usersPage
			)
		)
	}

	private fun listUsers(client: AuthRealmClient, realmId: String): List<UserRepresentation> {
		return client.users(realmId).list()
	}

	private fun listUsersOfGroup(client: AuthRealmClient, realmId: String, groupId: GroupId): List<UserRepresentation> {
		return client.getGroupResource(realmId, groupId).members()
	}
}
