package city.smartb.im.f2.organization.domain.model

import kotlin.js.JsExport

/**
 * @d2 model
 * @parent [city.smartb.im.f2.organization.domain.D2OrganizationPage]
 * @order 40
 */
enum class OrganizationStatus {
    /** Organization is waiting for validation to be able to use the platform */
    PENDING,
    /** Organization has been validated and can use features of the platform */
    VALIDATED,
    /** Organization has been rejected */
    REJECTED
}

@JsExport
object OrganizationStatusValues {
    fun pending() = OrganizationStatus.PENDING.name
    fun validated() = OrganizationStatus.VALIDATED.name
    fun rejected() = OrganizationStatus.REJECTED.name
}
