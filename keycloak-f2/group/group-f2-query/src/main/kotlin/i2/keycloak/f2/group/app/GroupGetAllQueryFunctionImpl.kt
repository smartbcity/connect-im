package i2.keycloak.f2.group.app

import i2.commons.error.I2ApiError
import i2.commons.error.asI2Exception
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.app.model.asModel
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryFunction
import i2.keycloak.f2.group.domain.features.query.GroupGetAllQueryResult
import i2.keycloak.realm.client.config.AuthRealmClient
import org.keycloak.representations.idm.GroupRepresentation
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
			var groups = client.groups(cmd.realmId).groups("", 0, client.countAllGroups(cmd.realmId), false)
			if (cmd.name != null) {
				groups = groups.filter { group -> group.name.contains(cmd.name!!, true) }
			}
			if (cmd.role != null) {
				groups = groups.filter { group -> group.realmRoles.contains(cmd.role) }
			}
			groups.map(GroupRepresentation::asModel).let(::GroupGetAllQueryResult)
		} catch (e: NotFoundException) {
			GroupGetAllQueryResult(emptyList())
		} catch (e: Exception) {
			val msg = "Error fetching Groups"
			logger.error(msg, e)
			throw I2ApiError(
				description = msg,
				payload = emptyMap()
			).asI2Exception()
		}
	}

	private fun AuthRealmClient.countAllGroups(realmId: String): Int {
		return this.groups(realmId).count()["count"]!!.toInt()
	}
}
