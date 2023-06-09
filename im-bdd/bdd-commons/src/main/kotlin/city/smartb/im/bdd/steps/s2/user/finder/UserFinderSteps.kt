package city.smartb.im.bdd.steps.s2.user.finder

import city.smartb.im.bdd.CucumberStepsDefinition
import city.smartb.im.bdd.assertion.AssertionBdd
import city.smartb.im.bdd.data.TestContextKey
import city.smartb.im.bdd.data.parser.safeExtract
import city.smartb.im.bdd.steps.s2.user.assertion.user
import city.smartb.im.user.api.UserEndpoint
import city.smartb.im.user.domain.model.User
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired

class UserFinderSteps: En, CucumberStepsDefinition() {

    @Autowired
    private lateinit var userEndpoint: UserEndpoint

    init {
        DataTableType { entry: Map<String, String> ->
            FetchByIdParams(
                identifier = entry.safeExtract("identifier")
            )
        }

        DataTableType { entry: Map<String, String> ->
            UserFetchedParams(
                identifier = entry.safeExtract("identifier")
            )
        }

        Then("I should receive the user") {
            step {
                assertUsersFetched(listOf(context.userIds.lastUsedKey))
            }
        }

        Then("I should receive null instead of a user") {
            step {
                assertUsersFetched(emptyList())
            }
        }

        Then("I should receive a list of users:") { dataTable: DataTable ->
            step {
                dataTable.asList(UserFetchedParams::class.java)
                    .map(UserFetchedParams::identifier)
                    .let { assertUsersFetched(it) }
            }
        }


    }

    private suspend fun assertUsersFetched(identifiers: List<TestContextKey>) {
        val fetchedIds = context.fetched.users.map(User::id)
        val expectedIds = identifiers.map(context.userIds::safeGet).toTypedArray()
        Assertions.assertThat(fetchedIds).containsExactlyInAnyOrder(*expectedIds)

        val userAsserter = AssertionBdd.user(userEndpoint)
        context.fetched.users.forEach { user ->
            userAsserter.assertThat(user.id).matches(user)
        }
    }

    private data class FetchByIdParams(
        val identifier: TestContextKey
    )

    private data class UserFetchedParams(
        val identifier: TestContextKey
    )
}
