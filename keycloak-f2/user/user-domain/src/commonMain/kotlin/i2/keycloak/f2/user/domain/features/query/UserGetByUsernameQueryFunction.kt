package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetByUsernameQueryFunction = F2Function<UserGetByUsernameQuery, UserGetByUsernameQueryResult>

@JsExport
@JsName("UserGetByUsernameQuery")
class UserGetByUsernameQuery(
	val realmId: RealmId,
	val auth: AuthRealm,
	val username: String
): Command

@JsExport
@JsName("UserGetByUsernameQueryResult")
class UserGetByUsernameQueryResult(
	val user: UserModel?
): Event
