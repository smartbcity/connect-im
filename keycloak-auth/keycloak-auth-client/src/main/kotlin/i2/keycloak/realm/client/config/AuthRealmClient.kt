package i2.keycloak.realm.client.config

import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.ClientResource
import org.keycloak.admin.client.resource.ClientsResource
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.RoleResource
import org.keycloak.admin.client.resource.RolesResource
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
		return getRealmResource(realmId).clients()
	}

	fun getClientResource(realmId: RealmId, id: String): ClientResource {
		return clients(realmId).get(id)!!
	}

	fun users(): UsersResource {
		return realm.users()
	}

	fun users(realmId: RealmId): UsersResource {
		return getRealmResource(realmId).users()
	}

	fun getUserResource(id: String): UserResource {
		return users().get(id)
	}

	fun getUserResource(realmId: RealmId, id: String): UserResource {
		return users(realmId).get(id)!!
	}

	fun roles(): RolesResource {
		return realm.roles()
	}

	fun roles(realmId: RealmId): RolesResource {
		return getRealmResource(realmId).roles()
	}

	fun getRoleResource(id: String): RoleResource {
		return roles().get(id)
	}

	fun getRoleResource(realmId: RealmId, id: String): RoleResource {
		return roles(realmId).get(id)!!
	}

	fun getRealmResource(realmId: RealmId): RealmResource {
		return keycloak.realm(realmId)
	}
}