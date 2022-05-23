package i2.keycloak.f2.user.app

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsFunction
import i2.keycloak.f2.user.domain.features.command.UserEmailSendActionsResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserEmailSendActionsFunctionImpl {

    @Bean
    fun userEmailSendActionsFunction(): UserEmailSendActionsFunction = keycloakF2Function { cmd, client ->
        try {
            if (cmd.redirectUri != null && cmd.clientId == null) {
                throw IllegalArgumentException("Params clientId must be defined if redirectUri is not null")
            }

            client.getUserResource(cmd.realmId, cmd.userId)
                .executeActionsEmail(cmd.clientId, cmd.redirectUri, cmd.actions)

            UserEmailSendActionsResult(cmd.userId)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] User[${cmd.userId}] Error sending email actions ${cmd.actions}",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
