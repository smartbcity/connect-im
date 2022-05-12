package i2.keycloak.f2.user.domain.model

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Representation of roles for the user.
 * @D2 model
 * @parent [i2.f2.user.domain.D2UserModelSection]
 */
@JsExport
@JsName("UserRoles")
class UserRoles(
    /**
     * Roles assigned to the user.
     * @example [["admin"]]
     */
    val assignedRoles: List<String>,
    /**
     * Effective roles assigned to the user. Multiple effective roles can be contained in a role.
     * @example [["admin", "write_user", "read_user", "write_organization", "read_organization"]]
     */
    val effectiveRoles: List<String>
)
