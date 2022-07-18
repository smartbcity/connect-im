package i2.keycloak.f2.user.domain.model

import i2.keycloak.master.domain.RealmId

fun defaultRealmRole(realmId: RealmId) = "default-roles-$realmId"
