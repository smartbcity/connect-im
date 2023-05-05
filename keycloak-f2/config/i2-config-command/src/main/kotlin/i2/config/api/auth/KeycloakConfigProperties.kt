package i2.config.api.auth

class KeycloakConfigProperties (
    val users: List<KeycloakUserConfig>?,
    val roles: List<String>?,
    val roleComposites: Map<String, List<String>>?,
    val webClients: List<WebClient>,
    val appClients: List<AppClient>
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
    val clientId: String,
    val webUrl: String
)

class AppClient (
    val clientId: String,
    val clientSecret: String?,
    val roles: Array<String>?,
    val realmManagementRoles: Array<String>?
)
