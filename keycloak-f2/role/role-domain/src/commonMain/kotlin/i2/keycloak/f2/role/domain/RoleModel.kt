package i2.keycloak.f2.role.domain

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Unique identifier of the role.
 * @d2 model
 * @parent [i2.f2.role.domain.D2RoleModelSection]
 * @visual json "b35824ed-933f-4a28-b373-fed0e6088f8b"
 * @order 10
 */
typealias RoleId = String

/**
 * Name of the role.
 * @d2 model
 * @parent [i2.f2.role.domain.D2RoleModelSection]
 * @visual json "Carrier"
 * @order 20
 */
typealias RoleName = String

/**
 * Representation of the role.
 * @d2 model
 * @order 30
 */
@JsExport
@JsName("Role")
class RoleModel(
    /**
     * Identifier of the role.
     */
    val id: RoleId,

    /**
     * Name of the role.
     */
    val name: RoleName,

    /**
     * Description of the role.
     * @example "A carrier is someone that carries things."
     */
    val description: String?,

    /**
     * Whether it is a client role or not.
     * @example "true"
     */
    val isClientRole: Boolean,
)
