package i2.keycloak.master.client.config

import i2.keycloak.master.domain.MasterRealmAuth
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder

class MasterRealmClientBuilder {

	companion object {
		val REALM_MASTER = "master"
	}

	fun build(auth: MasterRealmAuth): MasterRealmClient {
		val keycloak = KeycloakBuilder.builder()
			.grantType(OAuth2Constants.PASSWORD)
			.realm(REALM_MASTER)
			.serverUrl(auth.serverUrl)
			.clientId(auth.clientId)
			.username(auth.username)
			.password(auth.password)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(4).build())
			.build()
		return MasterRealmClient(keycloak)
	}
}