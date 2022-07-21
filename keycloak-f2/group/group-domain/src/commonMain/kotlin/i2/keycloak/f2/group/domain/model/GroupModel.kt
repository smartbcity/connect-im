package i2.keycloak.f2.group.domain.model

typealias GroupId = String

data class GroupModel(
    val id: GroupId,
    val name: String,
    val attributes: Map<String, String>,
    val roles: List<String>,
    val enabled: Boolean
)
