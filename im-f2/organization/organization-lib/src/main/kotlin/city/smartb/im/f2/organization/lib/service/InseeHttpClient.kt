package city.smartb.im.f2.organization.lib.service

import city.smartb.im.api.config.properties.InseeProperties
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.f2.organization.lib.model.insee.InseeResponse

class InseeHttpClient(
    private val inseeProperties: InseeProperties
): ClientJvm(
    baseUrl = inseeProperties.sireneApi,
    generateBearerToken = { inseeProperties.token }
) {
    suspend fun getOrganizationBySiret(siret: String): InseeResponse {
        return get("siret/$siret")
    }
}
