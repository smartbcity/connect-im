package i2.keycloak.f2.client.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.features.query.ClientGetSecretFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetSecretResult
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.keycloak.representations.idm.CredentialRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class ClientGetSecretFunctionImpl {

    private val logger by Logger()

    @Bean
    fun clientGetSecretFunction(): ClientGetSecretFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getClientResource(cmd.realmId, cmd.clientId)
                .secret
                .asResult()
        } catch (e: Exception) {
            val msg = "Error fetching secret of client with id[${cmd.clientId}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }

    private fun CredentialRepresentation.asResult() = ClientGetSecretResult(value)
}
