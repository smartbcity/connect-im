package i2.keycloak.f2.user.app

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.app.model.asModels
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserPageFunction
import i2.keycloak.f2.user.domain.features.query.UserPageResult
import i2.keycloak.f2.user.domain.model.UserModel
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
	fun userPageFunctionImpl(): UserPageFunction = keycloakF2Function { query, realmClient ->
		val userRepresentations = if (query.groupId == null) {
			listUsers(realmClient, query.realmId)
		} else {
			listUsersOfGroup(realmClient, query.realmId, query.groupId!!)
		}

		var users = userRepresentations.asModels { userId ->
			userFinderService.getRoles(userId, query.realmId, query.auth)
		}.asSequence()

		if (!query.withDisabled) {
			users = users.filter(UserModel::enabled)
		}

		query.search?.split(" ")
			?.map(String::trim)
			?.forEach { searchWord ->
				users = users.filter { user ->
					user.email.orEmpty().contains(searchWord, true) ||
							user.firstName.orEmpty().contains(searchWord, true) ||
							user.lastName.orEmpty().contains(searchWord, true)
				}
			}

		query.role?.let { roleFilter ->
			users = users.filter { user -> roleFilter in user.roles.assignedRoles }
		}

		query.attributes.forEach { (key, value) ->
			users = users.filter { user -> value == user.attributes[key] }
		}

		val count = users.count()
		val usersPage = if (query.page.size != null && query.page.page != null) {
			users.chunked(query.page.size!!).elementAtOrNull(query.page.page!!).orEmpty()
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
