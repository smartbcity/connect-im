package i2.keycloak.plugin.domain.model

import org.keycloak.events.EventType

data class KeycloakHttpEvent(
    val id: String,
    val time: Long,
    val type: EventType,
    val realmId: String,
    val clientId: String,
    val userId: String?,
    val sessionId: String?,
    val error: String?,
    val details: Map<String, String>?
)
