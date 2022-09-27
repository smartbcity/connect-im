package i2.keycloak.plugin.event.listener.http

import i2.keycloak.plugin.domain.model.KeycloakHttpEvent
import kotlinx.coroutines.runBlocking
import org.keycloak.events.Event
import org.keycloak.events.EventListenerProvider
import org.keycloak.events.admin.AdminEvent
import org.keycloak.models.KeycloakSession
import kotlin.streams.toList

class HttpEventListenerProvider(
    private val session: KeycloakSession
): EventListenerProvider {
    override fun onEvent(event: Event): Unit = runBlocking {
        val realm = session.realms().getRealm(event.realmId)
            ?: return@runBlocking

        val client = session.clients().getClientByClientId(realm, event.clientId)
            ?: return@runBlocking

        client.protocolMappersStream.toList()
            .firstOrNull { it.name == "event-http-webhook" }
            ?.config
            ?.get("claim.value")
            ?.let { url -> WebhookClient.send(url, event.toKeycloakHttpEvent()) }
    }

    override fun onEvent(event: AdminEvent, includeRepresentation: Boolean) {}
    override fun close() {}

    private fun Event.toKeycloakHttpEvent() = KeycloakHttpEvent(
        id = id,
        time = time,
        type = type,
        realmId = realmId,
        clientId = clientId,
        userId = userId,
        sessionId = sessionId,
        error = error,
        details = details
    )
}
