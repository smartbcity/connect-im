package i2.keycloak.f2.client.domain.features.query

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.ClientIdentifier
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.commons.domain.KeycloakF2Query
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetByClientIdentifierFunction = F2Function<ClientGetByClientIdentifierQuery, ClientGetByClientIdentifierResult>

@JsExport
@JsName("ClientGetByClientIdentifierQuery")
class ClientGetByClientIdentifierQuery(
    val clientIdentifier: ClientIdentifier,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Query

@JsExport
@JsName("ClientGetByClientIdentifierResult")
class ClientGetByClientIdentifierResult(
	val item: ClientModel?
): Event
