package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetByGroupIdQueryFunction = F2Function<UserGetByGroupIdQuery, UserGetByGroupIdQueryResult>

@JsExport
@JsName("UserGetByGroupIdQuery")
class UserGetByGroupIdQuery(
	val groupId: GroupId,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserGetByGroupIdQueryResult")
class UserGetByGroupIdQueryResult(
	val users: List<UserModel>
): Event
