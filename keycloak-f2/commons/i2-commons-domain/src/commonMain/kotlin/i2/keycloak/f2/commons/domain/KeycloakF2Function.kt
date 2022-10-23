package i2.keycloak.f2.commons.domain

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import i2.keycloak.master.domain.AuthRealm
import kotlin.js.JsName

//@JsExport
@JsName("KeycloakF2Command")
interface KeycloakF2Command: Command {
    val auth: AuthRealm
}

//@JsExport
@JsName("KeycloakF2Result")
interface KeycloakF2Result: Event
