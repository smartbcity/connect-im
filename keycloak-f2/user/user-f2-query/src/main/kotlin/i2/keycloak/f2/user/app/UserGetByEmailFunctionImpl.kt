package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.app.model.asModel
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByEmailResult
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class UserGetByEmailFunctionImpl {

	private val logger by Logger()

	@Bean
	fun userGetByEmailQueryFunction(userFinderService: UserFinderService)
			: UserGetByEmailFunction = keycloakF2Function { cmd, realmClient ->
		try {
			realmClient.users(cmd.realmId).list().first { user ->
				user.email == cmd.email
			}.asModel { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
				.asResult()
		} catch (e: NoSuchElementException) {
			UserGetByEmailResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with email[${cmd.email}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel.asResult(): UserGetByEmailResult {
		return UserGetByEmailResult(this)
	}
}
