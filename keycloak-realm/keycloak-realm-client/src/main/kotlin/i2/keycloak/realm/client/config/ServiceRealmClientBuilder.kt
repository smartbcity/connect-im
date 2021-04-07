package i2.keycloak.realm.client.config

import i2.keycloak.realm.domain.ServiceRealmAuth
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder

class ServiceRealmClientBuilder {

	fun build(auth: ServiceRealmAuth): ServiceRealmClient {
		val keycloak = KeycloakBuilder.builder()
			.serverUrl(auth.serverUrl)
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.realm(auth.realm)
			.clientId(auth.clientId)
			.clientSecret(auth.clientSecret)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(10).build())
			.build()
		val realm = keycloak.realm(auth.realm)
		return ServiceRealmClient(keycloak, realm, auth)
	}
}