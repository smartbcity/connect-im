package i2.keycloak.realm.client.config

import i2.keycloak.realm.domain.ServiceRealmAuth
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.admin.client.resource.UsersResource

class ServiceRealmClient(
	val keycloak: Keycloak,
	val realm: RealmResource,
	val auth: ServiceRealmAuth
) {

	fun users(): UsersResource {
		return realm.users()
	}

	fun getUserResource(id: String): UserResource {
		return users().get(id)
	}
}