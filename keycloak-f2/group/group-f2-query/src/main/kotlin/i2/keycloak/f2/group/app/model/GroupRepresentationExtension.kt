package i2.keycloak.f2.group.app.model

import i2.keycloak.f2.group.domain.model.GroupModel
import org.keycloak.representations.idm.GroupRepresentation

fun GroupRepresentation.asModel(getComposites: (roleName: String) -> List<String>) = GroupModel(
    id = id,
    name = name,
    attributes = attributes.orEmpty().mapValues { (_, value) -> value.first() },
    roles = realmRoles.orEmpty().flatMap{ getComposites(it) + it }.distinct(),
    creationDate = attributes[GroupModel::creationDate.name]?.firstOrNull()?.toLong() ?: 0
)
