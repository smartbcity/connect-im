package i2.keycloak.f2.user.domain.model

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("UserRoles")
class UserRoles(
    val assignedRoles: List<String>,
    val effectiveRoles: List<String>
)
