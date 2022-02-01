package i2.keycloak.f2.realm.domain.features.command

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.realm.domain.RealmId
import i2.keycloak.f2.realm.domain.RealmModel
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsExport
import kotlin.js.JsName

typealias RealmGetOneQueryFunction = F2Function<RealmGetOneQuery, RealmGetOneQueryResult>

@JsExport
@JsName("RealmGetOneQuery")
class RealmGetOneQuery(
    val id: RealmId,
    val authRealm: AuthRealm
): Command

@JsExport
@JsName("RealmGetOneQueryResult")
class RealmGetOneQueryResult(
	val realm: RealmModel?
): Event
