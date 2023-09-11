package city.smartb.im.bdd.core.user.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.f2.user.api.UserEndpoint
import city.smartb.im.f2.user.domain.query.UserPageQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class UserPageSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    init {
        DataTableType(::userPageParams)

        When ("I get a page of users") {
            step {
                fetchUserPage(userPageParams(null))
            }
        }

        When ("I get a page of users:") { params: UserPageParams ->
            step {
                fetchUserPage(params)
            }
        }
    }

    private suspend fun fetchUserPage(params: UserPageParams) {
        context.fetched.users = UserPageQuery(
            organizationId = params.organizationId,
            name = params.name,
            email = params.email,
            role = params.role,
            withDisabled = params.withDisable,
            offset = params.offset,
            limit = params.limit,
            attributes = null
        ).invokeWith(userEndpoint.userPage()).items
    }

    private fun userPageParams(entry: Map<String, String>?) = UserPageParams(
        organizationId = entry?.get("organizationId").parseNullableOrDefault(context.organizationIds.lastUsedOrNull),
        name = entry?.get("name"),
        email = entry?.get("email"),
        role = entry?.get("role"),
//        attributes = entry?.get("attr"),
        withDisable = entry?.get("withDisabled").toBoolean(),
        offset = entry?.get("offset")?.toInt(),
        limit = entry?.get("limit")?.toInt(),
    )

    private data class UserPageParams(
        val organizationId: OrganizationId?,
        val name: String?,
        val email: String?,
        val role: String?,
//        val attributes: Map<String, String>?,
        val withDisable: Boolean,
        val offset: Int?,
        val limit: Int?
    )
}
