package city.smartb.im.bdd.steps.s2.user.command

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.steps.s2.user.assertion.user
import city.smartb.im.commons.model.Address
import city.smartb.im.organization.domain.model.OrganizationId
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.features.command.UserCreateCommand
import f2.dsl.fnc.invoke
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import java.util.UUID
import org.springframework.beans.factory.annotation.Autowired

class UserCreateSteps: En, CucumberStepsDefinition() {
    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    private lateinit var command: UserCreateCommand

    init {
        DataTableType(::userCreateParams)

        When("I create a user") {
            step {
                createUser(userCreateParams(null))
            }
        }

        When("I create a user:") { params: UserCreateParams ->
            step {
                createUser(params)
            }
        }

        Given("A user is created") {
            step {
                createUser(userCreateParams(null))
            }
        }

        Given("A user is created:") { params: UserCreateParams ->
            step {
                createUser(params)
            }
        }

        Given("Some users are created:") { dataTable: DataTable ->
            step {
                dataTable.asList(UserCreateParams::class.java)
                    .forEach { createUser(it) }
            }
        }

        Then("The user should be created") {
            step {
                val userId = context.userIds.lastUsed

                AssertionBdd.user(userEndpoint).assertThat(userId).hasFields(
                    email = command.email,
                    givenName = command.givenName,
                    familyName = command.familyName,
                    address = command.address,
                    phone = command.phone,
                    roles = command.roles,
                    sendEmailLink = command.sendEmailLink,
                    memberOf = command.memberOf,
                    attributes = buildAttributesMap(command.attributes, command.memberOf).orEmpty(),
                )
            }
        }
    }

    private suspend fun createUser(params: UserCreateParams) = context.userIds.register(params.identifier) {
        command = UserCreateCommand(
            email = params.email,
            password = params.password,
            givenName = params.givenName,
            familyName = params.familyName,
            address = params.address,
            phone = params.phone,
            roles = params.roles,
            sendEmailLink = params.sendEmailLink,
            memberOf = params.memberOf,
            attributes = params.attributes,
            isEmailVerified = params.isEmailVerified
        )
        userEndpoint.userCreate().invoke(command).id
    }

    private fun userCreateParams(entry: Map<String, String>?): UserCreateParams {
        return UserCreateParams(
            identifier = entry?.get("identifier").orRandom(),
            email = entry?.get("email") ?: "user@test.com",
            password = entry?.get("password") ?: "azerty12345",
            givenName = entry?.get("givenName") ?: "John",
            familyName = entry?.get("familyName") ?: "Deuf",
            address = Address(
                street = "street",
                postalCode = "12345",
                city = "city"
            ),
            phone = entry?.get("phone") ?: "0600000000",
            roles = listOfNotNull(context.roleIds.lastUsedOrNull),
            sendEmailLink = false,
            memberOf = entry?.get("memberOf") ?: context.organizationIds.lastUsedOrNull,
            attributes = userAttributesParams(entry),
            isEmailVerified = true
        )
    }

    private data class UserCreateParams(
        val identifier: TestContextKey,
        val email: String,
        val password: String?,
        val givenName: String,
        val familyName: String,
        val address: Address?,
        val phone: String?,
        val roles: List<String>,
        val sendEmailLink: Boolean,
        val memberOf: OrganizationId?,
        val attributes: Map<String, String>,
        val isEmailVerified: Boolean
    )

    private fun userAttributesParams(entry: Map<String, String>?): Map<String, String> {
        val job = entry?.get("job") ?: "job-${UUID.randomUUID()}"
        return mapOf(
            "job" to job
        )
    }

    private fun buildAttributesMap(attributes: Map<String, String>?, memberOf: OrganizationId?): Map<String, String>? {
        if (memberOf == null) return attributes
        return command.attributes.orEmpty().plus("memberOf" to memberOf)
    }
}
