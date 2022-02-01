package i2.keycloak.f2.user.app

import f2.dsl.fnc.f2Function
import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.realm.domain.UserModel
import i2.keycloak.f2.realm.domain.features.query.UserGetByUsernameQueryFunction
import i2.keycloak.f2.realm.domain.features.query.UserGetByUsernameQueryResult
import i2.keycloak.f2.user.app.model.asModel
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class UserGetByUsernameQueryFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	private val logger by Logger()

	@Bean
	fun userGetByUsernameQueryFunction(): UserGetByUsernameQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		try {
			realmClient.users(cmd.realmId)
				.search(cmd.username)
				.firstOrNull()
				?.asModel { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
				.asResult()
		} catch (e: NoSuchElementException) {
			UserGetByUsernameQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with username[${cmd.username}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel?.asResult(): UserGetByUsernameQueryResult {
		return UserGetByUsernameQueryResult(this)
	}
}
