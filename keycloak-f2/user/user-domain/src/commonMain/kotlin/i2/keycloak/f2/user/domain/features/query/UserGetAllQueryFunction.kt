package i2.keycloak.f2.user.domain.features.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserGetAllQueryFunction = F2Function<UserGetAllQuery, UserGetAllQueryResult>

@JsExport
@JsName("UserGetAllQuery")
class UserGetAllQuery(
	val groupId: String? = null,
	val email: String? = null,
	val role: String? = null,
	val page: PagePagination,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserGetAllQueryResult")
class UserGetAllQueryResult(
	val users: Page<UserModel>
): Event
