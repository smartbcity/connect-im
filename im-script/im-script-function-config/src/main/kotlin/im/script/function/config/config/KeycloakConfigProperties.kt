package im.script.function.config.config

import i2.keycloak.f2.client.domain.ClientIdentifier
import im.script.function.core.model.AppClient

class KeycloakConfigProperties (
    val appClients: List<AppClient>,
    val webClients: List<WebClient>,
    val users: List<KeycloakUserConfig>?,
    val roles: List<String>?,
    val roleComposites: Map<String, List<String>>?,
)

class KeycloakUserConfig(
    val username: String,
    val email: String,
    val password: String?,
    val firstname: String,
    val lastname: String,
    val role: String
)

class WebClient (
    val clientId: ClientIdentifier,
    val webUrl: String
)