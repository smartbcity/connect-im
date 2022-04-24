package i2.keycloak.f2.user.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.user.domain.features.command.UserPasswordResetResult
import i2.keycloak.f2.user.domain.features.command.UserResetPasswordFunction
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserResetPasswordFunctionImpl {

	@Bean
	fun userResetPasswordFunction(): UserResetPasswordFunction = keycloakF2Function { cmd, client ->
		try {
			val userResource = client.getUserResource(cmd.realmId, cmd.userId)

			CredentialRepresentation().apply {
				type = CredentialRepresentation.PASSWORD
				isTemporary = false
				value = cmd.password
			}.let(userResource::resetPassword)

			UserPasswordResetResult(cmd.userId)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.userId}] Error resetting password",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}
}
