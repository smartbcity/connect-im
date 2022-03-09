package i2.keycloak.f2.role.domain

import kotlin.js.JsExport
import kotlin.js.JsName


typealias RoleId = String
typealias RoleName = String

@JsExport
@JsName("RoleModel")
class RoleModel(
    val id: RoleId,
    val name: RoleName,
    val description: String?,
    val isClientRole: Boolean,
)
