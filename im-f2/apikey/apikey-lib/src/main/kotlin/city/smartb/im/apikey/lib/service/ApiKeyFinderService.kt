package city.smartb.im.apikey.lib.service

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.apikey.domain.features.query.ApiKeyGetQuery
import city.smartb.im.apikey.domain.features.query.ApiKeyGetResult
import city.smartb.im.apikey.domain.features.query.ApiKeyPageQuery
import city.smartb.im.apikey.domain.features.query.ApiKeyPageResult
import city.smartb.im.infra.redis.CacheName
import city.smartb.im.infra.redis.RedisCache
import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.domain.features.query.GroupGetFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetQuery
import i2.keycloak.f2.group.domain.features.query.GroupPageFunction
import i2.keycloak.f2.group.domain.features.query.GroupPageQuery
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ApiKeyFinderService(
    private val groupGetFunction: GroupGetFunction,
    private val groupPageFunction: GroupPageFunction,
    private val authenticationResolver: ImAuthenticationProvider,
    private val redisCache: RedisCache,
) {

    private val logger = LoggerFactory.getLogger(ApiKeyFinderService::class.java)

    suspend fun apikeyGet(
        query: ApiKeyGetQuery,
    ): ApiKeyGetResult = redisCache.getFromCacheOr(CacheName.Apikey, query.id) {
        groupGetFunction.invoke(query.toGroupGetByIdQuery())
            .item
            ?.let { group -> group.toApiKeys().firstOrNull { it.id == query.id }}
            .let { dto -> ApiKeyGetResult(dto) }
    }


    suspend fun apikeyPage(
        query: ApiKeyPageQuery,
    ): ApiKeyPageResult {
        val result = groupPageFunction.invoke(query.toGroupPageQuery())
        val items = result.page.items.flatMap { it.toApiKeys() }
        return ApiKeyPageResult(
            items = items,
            total = result.page.total
        )
    }

    private suspend fun ApiKeyPageQuery.toGroupPageQuery(): GroupPageQuery {
        val auth = authenticationResolver.getAuth()
        return GroupPageQuery(
            search = search,
            groupId = organizationId,
            roles = listOfNotNull(role),
            attributes = attributes.orEmpty(),
            withDisabled = withDisabled ?: false,
            page = PagePagination(
                page = page,
                size = size
            ),
            realmId = auth.space,
            auth = auth
        )
    }

    private suspend fun ApiKeyGetQuery.toGroupGetByIdQuery(): GroupGetQuery {
        val auth = authenticationResolver.getAuth()
        return GroupGetQuery(
            id = organizationId,
            realmId = auth.space,
            auth = auth
        )
    }

}
