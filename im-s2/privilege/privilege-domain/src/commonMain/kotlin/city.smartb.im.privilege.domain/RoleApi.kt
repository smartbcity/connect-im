package city.smartb.im.privilege.domain

import city.smartb.im.privilege.domain.role.command.RoleDefineFunction
import city.smartb.im.privilege.domain.role.query.RoleGetFunction

/**
 * @d2 api
 * @parent [D2RolePage]
 */
interface RoleApi: RoleCommandApi, RoleQueryApi

interface RoleCommandApi {
    /** Create or update a role */
    fun roleDefine(): RoleDefineFunction
}

interface RoleQueryApi {
    /** Get a role by identifier */
    fun roleGet(): RoleGetFunction
}
