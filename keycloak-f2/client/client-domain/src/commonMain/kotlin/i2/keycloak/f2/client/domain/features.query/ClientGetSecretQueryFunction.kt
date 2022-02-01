package i2.keycloak.f2.client.domain.features.query

import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientGetSecretQueryFunction = F2Function<ClientGetSecretQuery, ClientGetSecretQueryResult>

@JsExport
@JsName("ClientGetSecretQuery")
class ClientGetSecretQuery(
    val clientId: ClientId,
    val realmId: RealmId,
    val auth: AuthRealm
)

@JsExport
@JsName("ClientGetSecretQueryResult")
class ClientGetSecretQueryResult(
    val secret: String?
)
