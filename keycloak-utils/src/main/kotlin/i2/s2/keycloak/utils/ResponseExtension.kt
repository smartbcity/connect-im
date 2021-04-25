package i2.s2.keycloak.utils

import i2.s2.errors.I2ApiError
import i2.s2.errors.asI2Exception
import javax.ws.rs.core.Response

fun Response.toEntityCreatedId(): String {
	return this.location.toString().substringAfterLast("/")
}

fun Response.isFailure(): Boolean {
	return this.status < 200 || this.status > 299
}

fun Response.onCreationFailure(entityName: String = "entity") {
	val msg = "Error creating $entityName (code: ${status}) }"
	throw I2ApiError(
		description = msg,
		payload = emptyMap()
	).asI2Exception()
}