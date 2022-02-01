package i2.keycloak.f2.user.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.realm.domain.UserModel
import i2.keycloak.f2.realm.domain.features.query.UserGetByEmailQueryFunction
import i2.keycloak.f2.realm.domain.features.query.UserGetByEmailQueryResult
import i2.keycloak.f2.user.app.model.asModel
import i2.keycloak.f2.user.app.service.UserFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class UserGetByEmailQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun userGetByEmailQueryFunction(userFinderService: UserFinderService)
			: UserGetByEmailQueryFunction = keycloakF2Function { cmd, realmClient ->
		try {
			realmClient.users(cmd.realmId).list().first { user ->
				user.email == cmd.email
			}
				.asModel { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
				.asResult()
		} catch (e: NoSuchElementException) {
			UserGetByEmailQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with email[${cmd.email}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel.asResult(): UserGetByEmailQueryResult {
		return UserGetByEmailQueryResult(this)
	}
}
