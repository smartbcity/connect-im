package i2.keycloak.f2.group.domain.model

typealias GroupId = String

data class GroupModel(
    val id: GroupId,
    val name: String,
    val attributes: Map<String, List<String>>,
    val roles: List<String>
)
