package city.smartb.im.bdd.core.privilege.role.query

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.privilege.api.RoleEndpoint
import city.smartb.im.privilege.domain.role.query.RoleGetQuery
import f2.dsl.fnc.invokeWith
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContextKey

class RoleGetSteps: En, ImCucumberStepsDefinition() {

    @Autowired
    private lateinit var roleEndpoint: RoleEndpoint

    init {
        DataTableType(::roleGetParams)

        When ("I get the role") {
            step {
                roleGet(roleGetParams(null))
            }
        }

        When ("I get the role:") { params: RoleGetParams ->
            step {
                roleGet(params)
            }
        }
    }

    private suspend fun roleGet(params: RoleGetParams) {
        context.fetched.roles = listOfNotNull(
            RoleGetQuery(
                identifier = context.roleIdentifiers[params.identifier] ?: params.identifier
            ).invokeWith(roleEndpoint.roleGet()).item
        )
    }

    private fun roleGetParams(entry: Map<String, String>?) = RoleGetParams(
        identifier = entry?.get("identifier") ?: context.roleIdentifiers.lastUsedKey
    )

    private data class RoleGetParams(
        val identifier: TestContextKey
    )
}
