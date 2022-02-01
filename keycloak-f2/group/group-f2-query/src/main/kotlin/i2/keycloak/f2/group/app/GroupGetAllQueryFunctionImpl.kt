package i2.keycloak.f2.group.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.app.model.asModel
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryResult
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.master.domain.RealmId
import i2.keycloak.realm.client.config.AuthRealmClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger
import javax.ws.rs.NotFoundException

@Configuration
class GroupGetAllQueryFunctionImpl {

	private val logger by Logger()

	@Bean
	fun groupGetAllQueryFunction(): GroupGetAllQueryFunction = keycloakF2Function { cmd, client ->
		try {
			client.groups(cmd.realmId)
				.groups(cmd.page * cmd.size, cmd.size)
				.filter { group -> group.name.contains(cmd.search, true) }
				.map { group -> client.getGroup(cmd.realmId, group.id) }
				.let(::GroupGetAllQueryResult)
		} catch (e: NotFoundException) {
			GroupGetAllQueryResult(emptyList())
		} catch (e: Exception) {
			val msg = "Error fetching Groups (page: ${cmd.page}, size: ${cmd.size})"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun AuthRealmClient.getGroup(realmId: RealmId, id: GroupId) = getGroupResource(realmId, id)
		.toRepresentation()
		.asModel()
}
