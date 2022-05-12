package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.f2.user.domain.model.UserRoles
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetRolesFunction = F2Function<UserGetRolesQuery, UserGetRolesResult>

@JsExport
@JsName("UserGetRolesQuery")
class UserGetRolesQuery(
    val userId: UserId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserGetRolesResult")
class UserGetRolesResult(
	val roles: UserRoles
): Event
