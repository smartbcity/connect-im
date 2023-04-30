package i2.init.api.auth.config

import i2.keycloak.master.domain.AuthRealmPassword
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

    @Value("\${i2.keycloak.username}")
    lateinit var username: String

    @Value("\${i2.keycloak.password}")
    lateinit var password: String

    @Bean
    fun authRealm() = AuthRealmPassword(
        serverUrl = serverUrl,
        realmId = realm,
        redirectUrl = "",
        clientId = clientId,
        username = username,
        password = password
    )
}
