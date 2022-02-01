package i2.keycloak.f2.client.app

import f2.dsl.fnc.f2Function
import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.client.domain.features.command.ClientUpdateUrisFunction
import i2.keycloak.f2.client.domain.features.command.ClientUpdateUrisResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientUpdateUrisFunctionImpl {

    @Bean
    fun clientUpdateUrisFunction(): ClientUpdateUrisFunction = f2Function { cmd ->
        try {
            val realmClient = AuthRealmClientBuilder().build(cmd.auth)
            val clientResource = realmClient.getClientResource(cmd.realmId, cmd.id)

            val clientRep = clientResource.toRepresentation()
            clientRep.baseUrl = cmd.baseUrl
            clientRep.redirectUris = cmd.redirectUris
            clientRep.rootUrl = cmd.rootUrl

            clientResource.update(clientRep)

            ClientUpdateUrisResult(cmd.id)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] Client[${cmd.id}] Error updating URIs",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
