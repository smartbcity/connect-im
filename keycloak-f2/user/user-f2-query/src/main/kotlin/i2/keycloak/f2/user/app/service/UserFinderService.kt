package i2.keycloak.f2.user.app.service

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.realm.domain.UserId
import i2.keycloak.f2.realm.domain.features.query.UserGetRolesQuery
import i2.keycloak.f2.realm.domain.features.query.UserGetRolesQueryFunction
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.springframework.stereotype.Service

@Service
class UserFinderService(
    private val userGetRolesQueryFunction: UserGetRolesQueryFunction
) {
    suspend fun getRoles(userId: UserId, realmId: RealmId, authRealm: AuthRealm) = UserGetRolesQuery(
        userId = userId,
        realmId = realmId,
        auth = authRealm
    ).invokeWith(userGetRolesQueryFunction).roles
}
