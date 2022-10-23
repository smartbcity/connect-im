package i2.keycloak.f2.group.app.model

import i2.keycloak.f2.group.domain.model.GroupModel
import org.keycloak.representations.idm.GroupRepresentation

val i2GroupAttributes = setOf(
    GroupModel::enabled.name
)

fun GroupRepresentation.asModel(getComposites: (roleName: String) -> List<String>): GroupModel {
    val parsedAttributes = attributes.orEmpty()
        .mapValues { (_, value) -> value.first() }

    return GroupModel(
        id = id,
        name = name,
        attributes = parsedAttributes.filterKeys { it !in i2GroupAttributes },
        roles = realmRoles.orEmpty().flatMap{ getComposites(it) + it }.distinct(),
        enabled = parsedAttributes[GroupModel::enabled.name]?.toBoolean() ?: true
    )
}
