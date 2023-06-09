package city.smartb.im.bdd.steps.s2.organization.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.steps.s2.organization.assertion.organization
import city.smartb.im.organization.api.OrganizationEndpoint
import city.smartb.im.organization.domain.features.command.OrganizationDeleteCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired

class OrganizationDeleteSteps: En, CucumberStepsDefinition() {
    @Autowired
    private lateinit var organizationEndpoint: OrganizationEndpoint

    private lateinit var command: OrganizationDeleteCommand

    init {
        DataTableType(::organizationDeleteParams)

        When("I delete an organization") {
            step {
                deleteOrganization(organizationDeleteParams(null))
            }
        }

        When("I delete an organization:") { params: OrganizationDeleteParams ->
            step {
                deleteOrganization(params)
            }
        }

        Given("An organization is deleted") {
            step {
                deleteOrganization(organizationDeleteParams(null))
            }
        }

        Given("An organization is deleted:") { params: OrganizationDeleteParams ->
            step {
                deleteOrganization(params)
            }
        }

        Given("Some organizations are deleted:") { dataTable: DataTable ->
            step {
                dataTable.asList(OrganizationDeleteParams::class.java)
                    .forEach { deleteOrganization(it) }
            }
        }

        Then("The organization should be deleted") {
            step {
                AssertionBdd.organization(organizationEndpoint).notExists(command.id)
            }
        }
    }

    private suspend fun deleteOrganization(params: OrganizationDeleteParams) {
        command = OrganizationDeleteCommand(
            id = context.organizationIds.safeGet(params.identifier)
        )
        organizationEndpoint.organizationDelete().invoke(command).id
    }

    private fun organizationDeleteParams(entry: Map<String, String>?): OrganizationDeleteParams {
        return OrganizationDeleteParams(
            identifier = entry?.get("identifier") ?: context.organizationIds.lastUsedKey
        )
    }

    private data class OrganizationDeleteParams(
        val identifier: TestContextKey
    )
}
