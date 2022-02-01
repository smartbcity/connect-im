package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserRolesGrantFunction = F2Function<UserRolesGrantCommand, UserRolesGrantedResult>

@JsExport
@JsName("UserRolesGrantCommand")
class UserRolesGrantCommand(
	val id: UserId,
	val roles: List<String>,
	val auth: AuthRealm,
	val realmId: RealmId = auth.realmId
): Command

@JsExport
@JsName("UserRolesGrantedResult")
class UserRolesGrantedResult(
	val id: String
): Event
