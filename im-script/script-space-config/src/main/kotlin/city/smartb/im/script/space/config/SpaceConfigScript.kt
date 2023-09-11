package city.smartb.im.script.space.config

import city.smartb.im.commons.auth.AuthContext
import city.smartb.im.commons.model.OrganizationId
import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.commons.utils.ParserUtils
import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.f2.organization.domain.command.OrganizationCreateCommandDTOBase
import city.smartb.im.f2.organization.domain.model.OrganizationStatus
import city.smartb.im.f2.organization.lib.OrganizationAggregateService
import city.smartb.im.f2.privilege.domain.model.PrivilegeDTO
import city.smartb.im.f2.privilege.lib.PrivilegeAggregateService
import city.smartb.im.f2.privilege.lib.PrivilegeFinderService
import city.smartb.im.f2.space.lib.SpaceFinderService
import city.smartb.im.f2.user.domain.command.UserCreateCommandDTOBase
import city.smartb.im.f2.user.lib.UserAggregateService
import city.smartb.im.f2.user.lib.UserFinderService
import city.smartb.im.script.core.config.properties.ImScriptSpaceProperties
import city.smartb.im.script.core.config.properties.toAuthRealm
import city.smartb.im.script.core.model.PermissionData
import city.smartb.im.script.core.model.RoleData
import city.smartb.im.script.core.service.ClientInitService
import city.smartb.im.script.space.config.config.OrganizationData
import city.smartb.im.script.space.config.config.SpaceConfigProperties
import city.smartb.im.script.space.config.config.UserData
import f2.spring.exception.ConflictException
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class SpaceConfigScript (
    private val clientInitService: ClientInitService,
    private val imScriptSpaceProperties: ImScriptSpaceProperties,
    private val organizationAggregateService: OrganizationAggregateService,
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService,
    private val userAggregateService: UserAggregateService,
    private val userFinderService: UserFinderService,
    private val spaceFinderService: SpaceFinderService
) {
    private val logger by Logger()

    suspend fun run() {
        val jsonPath = imScriptSpaceProperties.jsonConfig ?: return
        val properties = ParserUtils.getConfiguration(jsonPath, SpaceConfigProperties::class.java)

        val auth = imScriptSpaceProperties.auth.toAuthRealm(properties.space)
        withContext(AuthContext(auth)) {
            logger.info("Verify Realm[${properties.space}] exists...")
            verifySpace(properties.space)

            logger.info("Initializing Permissions...")
            initPermissions(properties.permissions)
            logger.info("Initialized Permissions")

            logger.info("Initializing Roles...")
            initRoles(properties.roles)
            logger.info("Initialized Roles")

            logger.info("Initializing Clients...")
            properties.webClients.forEach { clientInitService.initWebClient(it) }
            properties.appClients.forEach { clientInitService.initAppClient(it) }
            logger.info("Initialized Client")

            logger.info("Initializing Organizations...")
            initOrganizations(properties.organizations)
            logger.info("Initialized Organizations")

            logger.info("Initializing Users...")
            initUsers(properties.users)
            logger.info("Initialized Users")
        }
    }

    private suspend fun initPermissions(permissions: List<PermissionData>?) {
        if (permissions.isNullOrEmpty()) {
            return
        }

        permissions.mapAsync { permission ->
            privilegeFinderService.getPrivilegeOrNull(permission.name)
                ?: privilegeAggregateService.define(permission.toCommand())
        }
    }

    private suspend fun initRoles(roles: List<RoleData>?) {
        if (roles.isNullOrEmpty()) {
            return
        }

        val existingRoles = privilegeFinderService.listRoles()
            .plus(privilegeFinderService.listPermissions())
            .map(PrivilegeDTO::identifier)
            .toMutableSet()

        val remainingRoles = roles.filter { it.name !in existingRoles }.toMutableSet()
        var anyRoleInitialized = true

        while (remainingRoles.isNotEmpty() && anyRoleInitialized) {
            remainingRoles.filter { role -> role.permissions.orEmpty().all { it in existingRoles } }
                .also { anyRoleInitialized = it.isNotEmpty() }
                .mapAsync { role -> privilegeAggregateService.define(role.toCommand()) }
                .forEach { role ->
                    remainingRoles.removeIf { it.name == role.identifier }
                    existingRoles.add(role.identifier)
                }
        }

        if (remainingRoles.isNotEmpty()) {
            throw IllegalArgumentException(
                "Could not initialize roles [${remainingRoles.joinToString { it.name }}] because some of their permissions do not exist"
            )
        }

        roles.forEach { role ->
            privilegeFinderService.getPrivilegeOrNull(role.name)
                ?: privilegeAggregateService.define(role.toCommand())
        }
    }

    private suspend fun verifySpace(spaceIdentifier: SpaceIdentifier) {
        spaceFinderService.get(spaceIdentifier)
    }

    private suspend fun initOrganizations(organizations: List<OrganizationData>?) {
        organizations?.mapAsync { organization ->
            val organizationId = try {
                OrganizationCreateCommandDTOBase(
                    siret = organization.siret,
                    name = organization.name,
                    description = organization.description,
                    address = organization.address,
                    website = null,
                    roles = organization.roles,
                    parentOrganizationId = null,
                    attributes = organization.attributes,
                    status = OrganizationStatus.VALIDATED.name
                ).let { organizationAggregateService.create(it).id }
            } catch (e: ConflictException) {
                return@mapAsync
            }
            logger.info("Initializing Users of Organization [${organization.name}]...")
            initUsers(organization.users, organizationId)
            logger.info("Initialized Users of Organization [${organization.name}]...")
        }
    }

    private suspend fun initUsers(users: List<UserData>?, organizationId: OrganizationId? = null) {
        users?.mapAsync { user ->
            userFinderService.getByEmailOrNull(user.email)
                ?: UserCreateCommandDTOBase(
                    email = user.email,
                    password = user.password,
                    givenName = user.firstname,
                    familyName = user.lastname,
                    roles = user.roles,
                    memberOf = organizationId,
                    attributes = user.attributes,
                    sendVerifyEmail = false,
                    isEmailVerified = true,
                    isPasswordTemporary = false,
                    sendResetPassword = false
                ).let { userAggregateService.create(it) }
        }
    }
}
