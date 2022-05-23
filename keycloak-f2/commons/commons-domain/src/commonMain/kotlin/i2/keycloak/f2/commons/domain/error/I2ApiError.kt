package i2.keycloak.f2.commons.domain.error

import f2.dsl.cqrs.error.ErrorSeverityError

class I2ApiError(
	override val description: String,
	override val payload: Map<String, String>,
): I2Error(
	type = I2ApiError::class.simpleName!!,
	severity = ErrorSeverityError(),
	description = description,
	payload = payload,
)
