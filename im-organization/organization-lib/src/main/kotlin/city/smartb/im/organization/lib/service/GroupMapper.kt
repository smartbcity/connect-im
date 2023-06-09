package city.smartb.im.organization.lib.service

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.orEmpty
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.organization.domain.model.ApiKey
import city.smartb.im.organization.domain.model.Organization
import i2.keycloak.f2.group.domain.model.GroupModel
import org.springframework.stereotype.Component

@Component
class GroupMapper {

    fun toOrganization(group: GroupModel): Organization {
        return Organization(
            id = group.id,
            name = group.name,
            siret = group.attributes[Organization::siret.name].orEmpty(),
            address = group.attributes[Organization::address.name]?.parseJsonTo(Address::class.java).orEmpty(),
            description = group.attributes[Organization::description.name],
            website = group.attributes[Organization::website.name],
            attributes = group.attributes.filterKeys { key -> key !in imGroupAttributes },
            roles = group.roles.assignedRoles,
            rolesComposites = group.roles,
            apiKeys = group.attributes[Organization::apiKeys.name]?.parseJsonTo(Array<ApiKey>::class.java).orEmpty(),
            enabled = group.enabled,
            disabledBy = group.attributes[Organization::disabledBy.name],
            creationDate = group.attributes[Organization::creationDate.name]?.toLong() ?: 0,
            disabledDate = group.attributes[Organization::disabledDate.name]?.toLong()
        )
    }

    private val imGroupAttributes = listOf(
        Organization::address.name,
        Organization::apiKeys.name,
        Organization::creationDate.name,
        Organization::description.name,
        Organization::disabledBy.name,
        Organization::disabledDate.name,
        Organization::enabled.name,
        Organization::siret.name,
        Organization::website.name
    )

}
