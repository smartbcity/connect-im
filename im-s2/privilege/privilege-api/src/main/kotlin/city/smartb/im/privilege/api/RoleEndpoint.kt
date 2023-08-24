package city.smartb.im.privilege.api

import city.smartb.im.commons.auth.Roles
import city.smartb.im.commons.auth.policies.f2Function
import city.smartb.im.privilege.domain.RoleApi
import city.smartb.im.privilege.domain.role.command.RoleDefineFunction
import city.smartb.im.privilege.domain.role.query.RoleGetFunction
import city.smartb.im.privilege.domain.role.query.RoleGetResult
import city.smartb.im.privilege.lib.PrivilegeAggregateService
import city.smartb.im.privilege.lib.PrivilegeFinderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.annotation.security.RolesAllowed

@Configuration
class RoleEndpoint(
    private val privilegeAggregateService: PrivilegeAggregateService,
    private val privilegeFinderService: PrivilegeFinderService
): RoleApi {
    private val logger by Logger()

    @Bean
    @RolesAllowed(Roles.IM_ROLE_WRITE)
    override fun roleDefine(): RoleDefineFunction = f2Function { command ->
        logger.info("roleDefine: $command")
        privilegeAggregateService.define(command)
    }

    @Bean
    @RolesAllowed(Roles.IM_ROLE_READ)
    override fun roleGet(): RoleGetFunction = f2Function { query ->
        logger.info("roleGet: $query")
        privilegeFinderService.getRoleOrNull(query.realmId, query.identifier)
            .let(::RoleGetResult)
    }
}
