package i2.keycloak.f2.user.domain.features.command

import city.smartb.im.commons.model.AuthRealm
import city.smartb.im.commons.model.RealmId
import city.smartb.im.commons.model.UserId
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserDeleteFunction = F2Function<UserDeleteCommand, UserDeletedEvent>

@JsExport
@JsName("UserDeleteCommand")
class UserDeleteCommand(
    val id: UserId,
    val realmId: RealmId,
    override val auth: AuthRealm,
): KeycloakF2Command

@JsExport
@JsName("UserDeletedEvent")
class UserDeletedEvent(
	val id: UserId
): Event
