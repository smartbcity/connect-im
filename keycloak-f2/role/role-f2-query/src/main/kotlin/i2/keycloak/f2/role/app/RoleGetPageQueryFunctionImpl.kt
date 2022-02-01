package i2.keycloak.f2.role.app

import f2.dsl.cqrs.page.Page
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.role.domain.RoleModel
import i2.keycloak.f2.role.domain.features.query.RoleGetPageQueryFunction
import i2.keycloak.f2.role.domain.features.query.RoleGetPageQueryResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleGetPageQueryFunctionImpl {

	companion object {
		const val PAGE_SIZE = 10
		const val PAGE_NUMBER = 1
	}

	@Bean
	fun roleGetPageQueryFunction(): RoleGetPageQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)

		val size = cmd.page.size ?: PAGE_SIZE
		val page = cmd.page.page ?: PAGE_NUMBER
		val roles = realmClient.roles(cmd.realmId).list()

		roles.chunked(size)
			.getOrElse(page) { emptyList() }
			.asModels()
			.asResult(roles.size)
	}

	private fun List<RoleModel>.asResult(total: Int): RoleGetPageQueryResult {
		return RoleGetPageQueryResult(
			Page(
				total = total,
				list = this
			)
		)
	}
}
