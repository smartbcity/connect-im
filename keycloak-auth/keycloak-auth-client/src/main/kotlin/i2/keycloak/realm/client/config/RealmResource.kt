package i2.keycloak.realm.client.config

import org.keycloak.admin.client.resource.RealmsResource
import org.keycloak.representations.idm.RealmRepresentation

fun AuthRealmClient.realmsResource(): RealmsResource = keycloak.realms()

fun AuthRealmClient.buildRealmRepresentation(
	realm: String,
	smtpServer: Map<String, String>? = null,
	theme: String? = null,
	locale: String? = null,
): RealmRepresentation {
	val realms = RealmRepresentation()
	realms.realm = realm
	realms.accessTokenLifespan = 28800
	realms.ssoSessionIdleTimeout = 604800
	realms.ssoSessionMaxLifespan = 259200
	realms.actionTokenGeneratedByUserLifespan = 900
	realms.isVerifyEmail = true
	realms.isResetPasswordAllowed = true
	realms.passwordPolicy = "length(8)"
	realms.smtpServer = smtpServer
	realms.loginTheme = theme
	realms.accountTheme = "keycloak"
	realms.adminTheme = "keycloak"
	realms.emailTheme = theme
	realms.isInternationalizationEnabled = true

	realms.supportedLocales = setOf(theme)
	realms.defaultLocale = locale

	realms.isEnabled = true
	return realms
}