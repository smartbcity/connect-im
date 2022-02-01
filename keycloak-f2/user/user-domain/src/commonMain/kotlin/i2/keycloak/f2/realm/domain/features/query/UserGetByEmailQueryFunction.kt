package i2.keycloak.f2.realm.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.realm.domain.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetByEmailQueryFunction = F2Function<UserGetByEmailQuery, UserGetByEmailQueryResult>

@JsExport
@JsName("UserGetByEmailQuery")
class UserGetByEmailQuery(
	val email: String,
	val realmId: RealmId,
	override val auth: AuthRealm
): KeycloakF2Command

@JsExport
@JsName("UserGetByEmailQueryResult")
class UserGetByEmailQueryResult(
	val user: UserModel?
): Event
