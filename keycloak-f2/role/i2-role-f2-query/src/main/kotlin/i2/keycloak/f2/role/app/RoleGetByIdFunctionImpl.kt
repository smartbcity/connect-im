package i2.keycloak.f2.role.app

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.role.domain.features.query.RoleGetByIdQueryFunction
import i2.keycloak.f2.role.domain.features.query.RoleGetByIdResult
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.spring.utils.logger.Logger

@Configuration
class RoleGetByIdFunctionImpl {
    private val logger by Logger()

    @Bean
    fun roleGetById(): RoleGetByIdQueryFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getRoleResource(cmd.id)
                .toRepresentation()
                .asModel()
                .let(::RoleGetByIdResult)
        } catch (e: NotFoundException) {
            RoleGetByIdResult(null)
        } catch (e: Exception) {
            val msg = "Error fetching role with id[${cmd.id}]"
            logger.error(msg, e)
            throw I2ApiError(
                description = msg,
                payload = emptyMap()
            ).asI2Exception()
        }
    }
}
