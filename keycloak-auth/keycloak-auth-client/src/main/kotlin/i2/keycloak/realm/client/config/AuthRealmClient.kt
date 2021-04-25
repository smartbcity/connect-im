package i2.keycloak.realm.client.config

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.admin.client.resource.UsersResource

class AuthRealmClient(
	val keycloak: Keycloak,
	val realm: RealmResource,
	val auth: AuthRealm
) {

	fun users(): UsersResource {
		return realm.users()
	}

	fun getUserResource(id: String): UserResource {
		return users().get(id)
	}

	fun getUserResource(realmId: RealmId, id: String): UserResource {
		return keycloak.realm(realmId).users().get(id)!!
	}
}