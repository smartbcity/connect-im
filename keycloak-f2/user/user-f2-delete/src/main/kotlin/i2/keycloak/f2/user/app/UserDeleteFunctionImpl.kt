package i2.keycloak.f2.user.app

import f2.dsl.fnc.f2Function
import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserDeleteFunction
import i2.keycloak.f2.user.domain.features.command.UserDeletedResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserDeleteFunctionImpl {

	@Bean
	fun userDeleteFunction(): UserDeleteFunction = f2Function { cmd ->
		try {
			val realmClient = AuthRealmClientBuilder().build(cmd.auth)
			realmClient.getUserResource(cmd.realmId, cmd.id).remove()
			UserDeletedResult(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] Error deleting User[${cmd.id}]",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
