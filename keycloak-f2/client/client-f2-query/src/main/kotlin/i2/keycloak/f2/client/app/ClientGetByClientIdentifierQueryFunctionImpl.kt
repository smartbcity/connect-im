package i2.keycloak.f2.client.app

import f2.dsl.fnc.f2Function
import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQueryFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierQueryResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.keycloak.representations.idm.ClientRepresentation
import org.springframework.context.annotation.Bean
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

class ClientGetByClientIdentifierQueryFunctionImpl {

    private val logger by Logger()

    @Bean
    fun clientGetByClientIdentifierQueryFunction(): ClientGetByClientIdentifierQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getRealmResource(cmd.realmId).clients()
                .findByClientId(cmd.clientIdentifier)
                .firstOrNull()
                ?.asModel()
                .asResult()
        } catch (e: NotFoundException) {
            ClientGetByClientIdentifierQueryResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching client with client identifier[${cmd.clientIdentifier}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }

    private fun ClientRepresentation.asModel(): ClientModel {
        return ClientModel(
            id = this.id,
            clientIdentifier = this.clientId
        )
    }

    private fun ClientModel?.asResult(): ClientGetByClientIdentifierQueryResult {
        return ClientGetByClientIdentifierQueryResult(this)
    }
}
