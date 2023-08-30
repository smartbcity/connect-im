package im.script.function.core.service

import city.smartb.im.core.client.api.ClientCoreAggregateService
import city.smartb.im.core.client.api.ClientCoreFinderService
import city.smartb.im.core.client.domain.command.ClientCreateCommand
import city.smartb.im.core.client.domain.command.ClientGrantClientRolesCommand
import city.smartb.im.core.client.domain.command.ClientGrantRealmRolesCommand
import city.smartb.im.core.client.domain.model.ClientId
import im.script.function.core.model.AppClient
import im.script.function.core.model.WebClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

const val ORGANIZATION_ID_CLAIM_NAME = "memberOf"

@Service
class ClientInitService(
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
) {
    companion object {
        val DEFAULT_REALM_MANAGEMENT_ROLES = listOf(
            "create-client",
            "manage-clients",
            "manage-users",
            "manage-realm",
            "view-clients",
            "view-users"
        )
    }

    private val logger = LoggerFactory.getLogger(ClientInitService::class.java)

    suspend fun initAppClient(appClient: AppClient): ClientId {
        val existingClient = clientCoreFinderService.getByIdentifierOrNull(appClient.clientId)
        if (existingClient != null) {
            return existingClient.id
        }

        val secret = appClient.clientSecret ?: UUID.randomUUID().toString()

        val clientId = ClientCreateCommand(
            identifier = appClient.clientId,
            secret = secret,
            isPublicClient = false,
            isDirectAccessGrantsEnabled = false,
            isServiceAccountsEnabled = true,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = false,
        ).let { clientCoreAggregateService.create(it).id }

        appClient.roles?.let { roles ->
            ClientGrantRealmRolesCommand(
                id = clientId,
                roles = roles
            ).let { clientCoreAggregateService.grantRealmRoles(it) }
        }

        if (appClient.realmManagementRoles == null || appClient.realmManagementRoles.isNotEmpty()) {
            val realmManagementId = clientCoreFinderService.getByIdentifier("realm-management").id
            ClientGrantClientRolesCommand(
                id = clientId,
                providerClientId = realmManagementId,
                roles = appClient.realmManagementRoles ?: DEFAULT_REALM_MANAGEMENT_ROLES
            ).let { clientCoreAggregateService.grantClientRoles(it) }
        }

        logger.info("Client [${appClient.clientId}] secret: $secret")
        return clientId
    }

    suspend fun initWebClient(webClient: WebClient): ClientId {
        val existingClient = clientCoreFinderService.getByIdentifierOrNull(webClient.clientId)
        if (existingClient != null) {
            return existingClient.id
        }

        return ClientCreateCommand(
            identifier = webClient.clientId,
            isPublicClient = true,
            isDirectAccessGrantsEnabled = true,
            isServiceAccountsEnabled = false,
            authorizationServicesEnabled = false,
            isStandardFlowEnabled = true,
            rootUrl = webClient.webUrl,
            redirectUris = listOf(webClient.webUrl),
            baseUrl = webClient.webUrl,
            adminUrl = webClient.webUrl,
            webOrigins = listOf(webClient.webUrl),
            additionalAccessTokenClaim = listOf(ORGANIZATION_ID_CLAIM_NAME)
        ).let { clientCoreAggregateService.create(it).id }
    }
}
