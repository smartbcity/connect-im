package i2.keycloak.f2.user.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.realm.domain.features.command.UserResetPasswordFunction
import i2.keycloak.f2.realm.domain.features.command.UserResetPasswordResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserResetPasswordFunctionImpl {

	@Bean
	fun userResetPasswordFunction(): UserResetPasswordFunction = f2Function { cmd ->
		val realmClient = AuthRealmClientBuilder().build(cmd.auth)

		cmd.password?.let { password ->
			val credentials = CredentialRepresentation()
			credentials.type = CredentialRepresentation.PASSWORD
			credentials.isTemporary = true
			credentials.value = password
			realmClient.getUserResource(cmd.id).resetPassword(CredentialRepresentation())
		}
		UserResetPasswordResult(cmd.id)
	}

}
