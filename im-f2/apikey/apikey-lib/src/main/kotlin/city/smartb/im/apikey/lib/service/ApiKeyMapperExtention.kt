package city.smartb.im.apikey.lib.service

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.commons.utils.parseJsonTo
import city.smartb.im.core.organization.domain.model.Organization

const val ORGANIZATION_FIELD_API_KEYS = "apiKeys"
fun Organization.apiKeys(): List<ApiKey> {
    return attributes[ORGANIZATION_FIELD_API_KEYS]?.parseJsonTo(Array<ApiKey>::class.java).orEmpty()
}