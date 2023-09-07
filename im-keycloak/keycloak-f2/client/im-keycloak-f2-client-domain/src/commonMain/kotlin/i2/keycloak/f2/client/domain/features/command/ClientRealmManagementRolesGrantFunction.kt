package i2.keycloak.f2.client.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.ClientId
import city.smartb.im.commons.model.RealmId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

@Deprecated("Use ClientCoreAggregateService.grantClientRoles")
typealias ClientRealmManagementRolesGrantFunction =
        F2Function<ClientRealmManagementRolesGrantCommand, ClientRealmManagementRolesGrantedEvent>

@JsExport
@JsName("ClientRealmManagementRolesGrantCommand")
class ClientRealmManagementRolesGrantCommand(
    val id: ClientId,
    val roles: List<String>,
    override val auth: AuthRealm,
    val realmId: RealmId = auth.space
): KeycloakF2Command

@JsExport
@JsName("ClientRealmManagementRolesGrantedEvent")
class ClientRealmManagementRolesGrantedEvent(
    val id: String
): Event
