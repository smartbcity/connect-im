package i2.keycloak.realm.domain

class ServiceRealmAuth (
	val serverUrl: String,
	val realm: String,
	val clientId: String,
	val clientSecret: String,
	val redirectUrl: String
)