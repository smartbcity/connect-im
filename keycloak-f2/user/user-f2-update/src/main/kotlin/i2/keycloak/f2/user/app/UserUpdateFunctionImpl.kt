package i2.keycloak.f2.user.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserUpdateCommand
import i2.keycloak.f2.user.domain.features.command.UserUpdateFunction
import i2.keycloak.f2.user.domain.features.command.UserUpdateResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserUpdateFunctionImpl {

	@Bean
	fun userUpdateFunction(): UserUpdateFunction = f2Function { cmd ->
		try {
			val realmClient = AuthRealmClientBuilder().build(cmd.auth)
			val userResource = realmClient.getUserResource(cmd.realmId, cmd.userId)

			val userRepresentation = userResource.toRepresentation().apply {
				update(cmd)
			}

			userResource.update(userRepresentation)
			UserUpdateResult(cmd.userId)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] User[${cmd.userId}] Error updating",
				payload = emptyMap()
			).asI2Exception(e)
		}
	}

	private fun UserRepresentation.update(command: UserUpdateCommand) {
		this.firstName = command.firstname
		this.lastName = command.lastname

		command.metadata.forEach {
			this.singleAttribute(it.key, it.value)
		}

//		var emailHasChanged = false
		if (this.email != command.email) {
//			emailHasChanged = true
			this.isEmailVerified = false
		}
		this.email = command.email

//		if (emailHasChanged) sendEmailVerification(userId)
	}
}
