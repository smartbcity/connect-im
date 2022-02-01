package i2.keycloak.f2.commons.app

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.KeycloakF2Command
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.AuthRealmClientBuilder

fun <C: KeycloakF2Command, R: Any> keycloakF2Function(
    fcn: suspend (C, AuthRealmClient) -> R
): F2Function<C, R> =
	f2Function { cmd ->
		val client = AuthRealmClientBuilder().build(cmd.auth)
		fcn(cmd, client)
	}
