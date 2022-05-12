package i2.keycloak.f2.user.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserRolesGrantFunction = F2Function<UserRolesGrantCommand, UserRolesGrantResult>

@JsExport
@JsName("UserRolesGrantCommand")
class UserRolesGrantCommand(
    val id: UserId,
    val roles: List<String>,
    val auth: AuthRealm,
    val realmId: RealmId = auth.realmId,
    val clientId: String? = null
): Command

@JsExport
@JsName("UserRolesGrantResult")
class UserRolesGrantResult(
	val id: String
): Event
