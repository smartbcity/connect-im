package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.UserId
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserCreateFunction = F2Function<UserCreateCommand, UserCreatedResult>

@JsExport
@JsName("UserCreateCommand")
class UserCreateCommand(
	val realmId: RealmId,
	val username: String,
	val firstname: String?,
	val lastname: String?,
	val email: String,
	val isEnable: Boolean,
	val metadata: Map<String, String>,
	val auth: AuthRealm,
) : Command

@JsExport
@JsName("UserCreatedResult")
class UserCreatedResult(
	val id: UserId
) : Event
