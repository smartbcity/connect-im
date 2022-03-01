package i2.keycloak.f2.user.domain.model

import kotlin.js.JsExport
import kotlin.js.JsName

typealias UserId = String

@JsExport
@JsName("UserModel")
class UserModel(
	val id: UserId,
	val email: String?,
	val firstName: String?,
	val lastName: String?,
	val realmRoles: List<String>,
	val attributes: Map<String, List<String>>
)
