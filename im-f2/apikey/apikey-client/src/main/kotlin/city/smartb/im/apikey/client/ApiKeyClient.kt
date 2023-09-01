package city.smartb.im.apikey.client

import city.smartb.im.apikey.domain.ApiKeyCommandFeatures
import city.smartb.im.apikey.domain.ApiKeyQueryFeatures
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddFunction
import city.smartb.im.apikey.domain.features.command.ApiKeyOrganizationAddedEvent
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveEvent
import city.smartb.im.apikey.domain.features.command.ApikeyRemoveFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyGetFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyGetResult
import city.smartb.im.apikey.domain.features.query.ApiKeyPageFunction
import city.smartb.im.apikey.domain.features.query.ApiKeyPageResult
import city.smartb.im.commons.http.ClientBuilder
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.commons.http.HttpClientBuilderJvm
import f2.dsl.fnc.f2Function

class ApiKeyClient(
    url: String,
    httpClientBuilder: ClientBuilder = HttpClientBuilderJvm,
    generateBearerToken: suspend () -> String? = { null }
): ClientJvm(url, httpClientBuilder, generateBearerToken), ApiKeyCommandFeatures, ApiKeyQueryFeatures {

    override fun apiKeyCreate(): ApiKeyOrganizationAddFunction = f2Function { cmd ->
        post<List<ApiKeyOrganizationAddedEvent>>("apiKeyCreate", cmd).first()
    }

    override fun apiKeyRemove(): ApikeyRemoveFunction = f2Function { cmd ->
        post<List<ApikeyRemoveEvent>>("apiKeyRemove", cmd).first()
    }

    override fun apiKeyGet(): ApiKeyGetFunction = f2Function { query ->
        post<List<ApiKeyGetResult>>("apiKeyGet", query).first()
    }

    override fun apiKeyPage(): ApiKeyPageFunction = f2Function { query ->
        post<List<ApiKeyPageResult>>("apiKeyPage", query).first()
    }
}