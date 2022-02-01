package i2.keycloak.f2.group.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.app.model.asModel
import i2.keycloak.f2.group.domain.features.query.GroupGetByIdQueryFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetByIdQueryResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class GroupGetByIdQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun groupGetByIdQueryFunction(): GroupGetByIdQueryFunction = keycloakF2Function { cmd, client ->
		try {
			client.getGroupResource(cmd.realmId, cmd.id)
				.toRepresentation()
				.asModel()
				.let(::GroupGetByIdQueryResult)
		} catch (e: NotFoundException) {
			GroupGetByIdQueryResult(null)
		} catch (e: Exception) {
			val msg = "Error fetching Group with id[${cmd.id}]"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}
}
