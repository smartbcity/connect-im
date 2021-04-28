package i2.keycloak.realm.client.config

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.ClientResource
import org.keycloak.admin.client.resource.ClientsResource
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.admin.client.resource.UsersResource

class AuthRealmClient(
	val keycloak: Keycloak,
	val realm: RealmResource,
	val auth: AuthRealm
) {

	fun clients(): ClientsResource {
		return realm.clients()
	}

	fun clients(realmId: RealmId): ClientsResource {
		return keycloak.realm(realmId).clients()
	}

	fun getClientResource(id: String): ClientResource {
		return clients().get(id)
	}

	fun getClientResource(realmId: RealmId, id: String): ClientResource {
		return clients(realmId).get(id)!!
	}

	fun users(): UsersResource {
		return realm.users()
	}

	fun users(realmId: RealmId): UsersResource {
		return keycloak.realm(realmId).users()
	}

	fun getUserResource(id: String): UserResource {
		return users().get(id)
	}

	fun getUserResource(realmId: RealmId, id: String): UserResource {
		return users(realmId).get(id)!!
	}
}