package i2.keycloak.f2.user.app.model

import i2.keycloak.f2.user.domain.model.UserId
import i2.keycloak.f2.user.domain.model.UserModel
import org.keycloak.representations.idm.UserRepresentation

suspend fun List<UserRepresentation>.asModels(getRealmRoles: suspend (UserId) -> List<String>): List<UserModel>
		= map { user -> user.asModel(getRealmRoles) }

suspend fun UserRepresentation.asModel(getRealmRoles: suspend (UserId) -> List<String>): UserModel {
	return UserModel(
		id = id,
		email = email,
		firstName = firstName,
		lastName = lastName,
		realmRoles = getRealmRoles(id),
		attributes = attributes.orEmpty()
	)
}
