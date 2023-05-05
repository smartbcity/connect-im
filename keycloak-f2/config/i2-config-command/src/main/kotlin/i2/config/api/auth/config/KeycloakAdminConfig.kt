package i2.config.api.auth.config

import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakAdminConfig {

    @Value("\${i2.keycloak.auth-server-url}")
    lateinit var serverUrl: String

    @Value("\${i2.keycloak.realm}")
    lateinit var realm: String

    @Value("\${i2.keycloak.client-id}")
    lateinit var clientId: String

    @Value("\${i2.keycloak.client-secret}")
    lateinit var clientSecret: String

    @Bean
    fun authRealm() = AuthRealmClientSecret(
        serverUrl = serverUrl,
        realmId = realm,
        clientId = clientId,
        clientSecret = clientSecret,
        redirectUrl = null
    )
}
