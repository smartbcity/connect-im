package i2.keycloak.f2.user.app.model

import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.f2.user.domain.model.UserModel
import i2.keycloak.f2.user.domain.model.UserRoles
import org.keycloak.representations.idm.UserRepresentation

suspend fun Iterable<UserRepresentation>.asModels(getRealmRoles: suspend (UserId) -> UserRoles): List<UserModel>
		= map { user -> user.asModel(getRealmRoles) }

suspend fun UserRepresentation.asModel(getRealmRoles: suspend (UserId) -> UserRoles): UserModel {
	return UserModel(
		id = id,
		email = email,
		firstName = firstName,
		lastName = lastName,
		roles = getRealmRoles(id),
		attributes = attributes.orEmpty().mapValues { (_, value) -> value.first() },
		enabled = isEnabled,
		creationDate = createdTimestamp
	)
}
