package i2.keycloak.f2.user.app

import f2.dsl.cqrs.page.Page
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.app.model.asModels
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetPageQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetPageQueryResult
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserGetPageQueryFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	companion object {
		const val PAGE_SIZE = 10
		const val PAGE_NUMBER = 0
	}

	@Bean
	fun userGetPageQueryFunctionImpl(): UserGetPageQueryFunction = keycloakF2Function { cmd, realmClient ->
		val size = cmd.page.size ?: PAGE_SIZE
		val page = cmd.page.page ?: PAGE_NUMBER
		val first = page * size
		val max = first + size
		val count = realmClient.keycloak.realm(cmd.realmId).users().count()
		realmClient.keycloak.realm(cmd.realmId).users().list(first, max)
			.asModels { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
			.asResult(count)
	}

	private fun List<UserModel>.asResult(total: Int): UserGetPageQueryResult {
		return UserGetPageQueryResult(
			Page(
				total = total,
				list = this
			)
		)
	}

}
