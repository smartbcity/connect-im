package i2.keycloak.f2.user.app.service

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.user.domain.features.query.UserGetRolesQuery
import i2.keycloak.f2.user.domain.features.query.UserGetRolesQueryFunction
import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.f2.user.domain.model.UserRoles
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val userGetRolesQueryFunction: UserGetRolesQueryFunction
) {
    suspend fun getRoles(userId: UserId, realmId: RealmId, authRealm: AuthRealm): UserRoles {
        val query = UserGetRolesQuery(
            userId = userId,
            realmId = realmId,
            auth = authRealm
        ).invokeWith(userGetRolesQueryFunction)

        return UserRoles(
            assignedRoles = query.roles.assignedRoles,
            effectiveRoles = query.roles.effectiveRoles
        )
    }
}
