package i2.keycloak.f2.group.domain.features.query

import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.f2.commons.domain.KeycloakF2Result
import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId

typealias GroupGetAllQueryFunction = F2Function<GroupGetAllQuery, GroupGetAllQueryResult>

class GroupGetAllQuery(
	val name: String? = null,
	val role: String? = null,
	val page: PagePagination,
	val realmId: RealmId,
	override val auth: AuthRealm,
): KeycloakF2Command

class GroupGetAllQueryResult(
	val groups: Page<GroupModel>
): KeycloakF2Result
