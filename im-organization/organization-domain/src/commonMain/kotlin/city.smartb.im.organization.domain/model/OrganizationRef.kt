package city.smartb.im.organization.domain.model

import i2.keycloak.f2.role.domain.RoleName
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("OrganizationRefDTO")
interface OrganizationRefDTO {
    val id: OrganizationId
    val name: String
    val roles: List<String>
}

/**
 * Short representation of an organization.
 * @D2 model
 * @parent [city.smartb.im.organization.domain.D2OrganizationPage]
 * @order 30
 */
data class OrganizationRef(
    /**
     * Identifier of the organization.
     */
    override val id: OrganizationId,

    /**
     * Name of the organization.
     * @example [Organization.name]
     */
    override val name: String,

    /**
     * Assigned and effective roles of the organization.
     * @example [Organization.roles]
     */
    override val roles: List<RoleName>
): OrganizationRefDTO
