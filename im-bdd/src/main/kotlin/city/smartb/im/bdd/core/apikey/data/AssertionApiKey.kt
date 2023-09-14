package city.smartb.im.bdd.core.apikey.data

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.apikey.domain.model.ApiKeyId
import city.smartb.im.apikey.domain.model.ApiKeyIdentifier
import city.smartb.im.apikey.domain.model.ApiKeyModel
import city.smartb.im.apikey.lib.service.ORGANIZATION_FIELD_API_KEYS
import city.smartb.im.commons.utils.parseJson
import city.smartb.im.f2.privilege.domain.role.model.Role
import city.smartb.im.infra.keycloak.client.KeycloakClient
import org.assertj.core.api.Assertions
import s2.bdd.assertion.AssertionBdd
import s2.bdd.repository.AssertionApiEntity

fun AssertionBdd.apiKey(client: KeycloakClient) = AssertionApiKey(client)

class AssertionApiKey(
    private val client: KeycloakClient
): AssertionApiEntity<ApiKeyModel, ApiKeyId, AssertionApiKey.ApiKeyAssert>() {
    override suspend fun findById(id: ApiKeyId): ApiKeyModel? {
        try {
            val organizationId = client.client(id)
                .serviceAccountUser
                .attributes["memberOf"]
                ?.firstOrNull()
                ?: return null

            val organization = client.group(organizationId).toRepresentation()
            val apiKeys = organization.attributes[ORGANIZATION_FIELD_API_KEYS]?.firstOrNull()?.parseJson<Array<ApiKeyModel>>()
            return apiKeys?.firstOrNull { it.id == id }
        } catch (e: javax.ws.rs.NotFoundException) {
            return null
        }
    }
    override suspend fun assertThat(entity: ApiKeyModel) = ApiKeyAssert(entity)

    inner class ApiKeyAssert(
        private val apiKey: ApiKeyModel
    ) {
        fun hasFields(
            id: ApiKeyId = apiKey.id,
            identifier: ApiKeyIdentifier = apiKey.identifier,
            name: String = apiKey.name,
            roles: List<String>? = apiKey.roles,
        ) = also {
            Assertions.assertThat(apiKey.id).isEqualTo(id)
            Assertions.assertThat(apiKey.identifier).isEqualTo(identifier)
            Assertions.assertThat(apiKey.name).isEqualTo(name)
            Assertions.assertThat(roles).isEqualTo(roles)
        }

        fun matches(apiKey: ApiKey) = hasFields(
            id = apiKey.id,
            identifier = apiKey.identifier,
            name = apiKey.name,
            roles = apiKey.roles.map(Role::identifier),
        )
    }
}
