package i2.keycloak.realm.client.config

import i2.keycloak.master.domain.AuthRealmClientSecret
import i2.keycloak.master.domain.AuthRealmPassword
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.AuthRealmException
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder

class AuthRealmClientBuilder {

	fun build(auth: AuthRealm): AuthRealmClient {
		return when(auth) {
			is AuthRealmPassword -> init(auth)
			is AuthRealmClientSecret ->  init(auth)
			else -> throw AuthRealmException("Invalid AuthRealm type[${auth::class.simpleName}]")
		}
	}

	private fun init(auth: AuthRealmClientSecret): AuthRealmClient {
		val keycloak = KeycloakBuilder.builder()
			.serverUrl(auth.serverUrl)
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.realm(auth.realm)
			.clientId(auth.clientId)
			.clientSecret(auth.clientSecret)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(10).build())
			.build()
		val realm = keycloak.realm(auth.realm)
		return AuthRealmClient(keycloak, realm, auth)
	}

	private fun init(auth: AuthRealmPassword): AuthRealmClient {
		val keycloak = KeycloakBuilder.builder()
			.serverUrl(auth.serverUrl)
			.grantType(OAuth2Constants.PASSWORD)
			.realm(auth.realm)
			.clientId(auth.clientId)
			.username(auth.username)
			.password(auth.password)
			.resteasyClient(ResteasyClientBuilder().connectionPoolSize(10).build())
			.build()
		val realm = keycloak.realm(auth.realm)
		return AuthRealmClient(keycloak, realm, auth)
	}
}