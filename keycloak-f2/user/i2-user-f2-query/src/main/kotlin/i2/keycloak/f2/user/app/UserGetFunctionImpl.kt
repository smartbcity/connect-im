package i2.keycloak.f2.user.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.app.model.asModel
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetFunction
import i2.keycloak.f2.user.domain.features.query.UserGetResult
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class UserGetFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	private val logger by Logger()

	@Bean
	fun userGetByIdQueryFunction(): UserGetFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		try {
			realmClient.getUserResource(cmd.realmId, cmd.id)
				.toRepresentation()
				.asModel { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
				.asResult()
		} catch (e: NotFoundException) {
			UserGetResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel.asResult(): UserGetResult {
		return UserGetResult(this)
	}
}
