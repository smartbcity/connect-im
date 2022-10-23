package i2.keycloak.f2.client.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantedEvent
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientServiceAccountRolesGrantFunctionImpl {

    @Bean
    fun clientServiceAccountRolesGrantFunction(): ClientServiceAccountRolesGrantFunction = f2Function { cmd ->
        try {
            val realmClient = AuthRealmClientBuilder().build(cmd.auth)

            val targetClientKeycloakId = realmClient.clients(cmd.realmId).findByClientId(cmd.id).first().id
            val targetClientResource = realmClient.getClientResource(cmd.realmId, targetClientKeycloakId)

            val roleProviderClientKeycloakId = realmClient.clients(cmd.realmId).findByClientId("realm-management").first().id

            val rolesToAdd = cmd.roles.map { role ->
                realmClient.getClientResource(cmd.realmId, roleProviderClientKeycloakId).roles().get(role).toRepresentation()
            }
            realmClient
                .getUserResource(cmd.realmId, targetClientResource.serviceAccountUser.id)
                .roles()
                .clientLevel(roleProviderClientKeycloakId)
                .add(rolesToAdd)

            ClientServiceAccountRolesGrantedEvent(cmd.id)
        } catch (e: Exception) {
            throw I2ApiError(
                description = "Realm[${cmd.realmId}] Client[${cmd.id}] Error granting roles",
                payload = emptyMap()
            ).asI2Exception(e)
        }
    }
}
