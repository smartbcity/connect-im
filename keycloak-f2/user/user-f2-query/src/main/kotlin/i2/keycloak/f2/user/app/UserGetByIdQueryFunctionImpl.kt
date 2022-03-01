package i2.keycloak.f2.user.app

import f2.dsl.fnc.f2Function
import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.user.app.model.asModel
import i2.keycloak.f2.user.app.service.UserFinderService
import i2.keycloak.f2.user.domain.features.query.UserGetByIdQueryFunction
import i2.keycloak.f2.user.domain.features.query.UserGetByIdQueryResult
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class UserGetByIdQueryFunctionImpl {

	@Autowired
	private lateinit var userFinderService: UserFinderService

	private val logger by Logger()

	@Bean
	fun userGetByIdQueryFunction(): UserGetByIdQueryFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)
		try {
			realmClient.getUserResource(cmd.realmId, cmd.id)
				.toRepresentation()
				.asModel { userId -> userFinderService.getRoles(userId, cmd.realmId, cmd.auth) }
				.asResult()
		} catch (e: NotFoundException) {
			UserGetByIdQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching User with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun UserModel.asResult(): UserGetByIdQueryResult {
		return UserGetByIdQueryResult(this)
	}
}
