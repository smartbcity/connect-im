package city.smartb.im.api.config.properties

import city.smartb.f2.spring.boot.auth.AuthenticationProvider
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmClientSecret
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "connect.im")
data class IMProperties (
    val organization: OrganizationProperties?,
    val smtp: Map<String, String>? = null,
    val theme: String? = null,
    val user: UserProperties?,
    val keycloak: KeycloakProperties
)
class OrganizationProperties(
    val insee: InseeProperties?
)
class UserProperties(
    val action: UserActionProperties?
)

class KeycloakProperties(
    val url: String,
    val realm: String,
    val clientId: String,
    val clientSecret: String
)

class UserActionProperties(
    val useJwtClientId: Boolean? = false
)

class InseeProperties(
    val sireneApi: String,
    val token: String
)

suspend fun KeycloakProperties.toAuthRealm(): AuthRealm {
    val realmId = AuthenticationProvider.getIssuer().split("/").last()
    return AuthRealmClientSecret(
        serverUrl = url,
        realmId   = realmId,
        clientId = clientId,
        clientSecret = clientSecret,
        redirectUrl = null
    )
}
