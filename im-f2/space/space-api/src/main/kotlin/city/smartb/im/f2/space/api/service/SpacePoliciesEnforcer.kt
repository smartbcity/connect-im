package city.smartb.im.f2.space.api.service

import city.smartb.im.commons.auth.policies.PolicyEnforcer
import city.smartb.im.commons.model.SpaceIdentifier
import city.smartb.im.f2.space.domain.policies.SpacePolicies
import org.springframework.stereotype.Service

@Service
class SpacePoliciesEnforcer: PolicyEnforcer() {

    suspend fun checkGet(spaceIdentifier: SpaceIdentifier) = checkAuthed("get space") { authedUser ->
        SpacePolicies.canGet(authedUser)
    }

    suspend fun checkPage() = checkAuthed("page spaces") { authedUser ->
        SpacePolicies.canPage(authedUser)
    }

    suspend fun checkCreate() = checkAuthed("create a space") { authedUser ->
        SpacePolicies.canCreate(authedUser)
    }

    suspend fun checkDelete(spaceIdentifier: SpaceIdentifier) = checkAuthed("delete a space") { authedUser ->
        SpacePolicies.canDelete(authedUser, spaceIdentifier)
    }

}
