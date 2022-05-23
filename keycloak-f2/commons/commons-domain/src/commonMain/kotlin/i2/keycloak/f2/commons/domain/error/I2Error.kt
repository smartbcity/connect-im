package i2.keycloak.f2.commons.domain.error

import f2.dsl.cqrs.error.Error
import f2.dsl.cqrs.error.ErrorSeverity
import f2.dsl.cqrs.error.ErrorSeverityError
import kotlinx.datetime.Clock

open class I2Error(
	type: String,
	description: String,
	payload: Map<String, String>,
	date: String = Clock.System.now().toString(),
	severity: ErrorSeverity = ErrorSeverityError()
): Error<Map<String, String>>(type, description, date, payload, severity)
