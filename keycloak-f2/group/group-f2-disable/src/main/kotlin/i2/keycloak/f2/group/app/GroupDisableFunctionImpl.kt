package i2.keycloak.f2.group.app

import f2.dsl.cqrs.page.PagePagination
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.group.domain.features.command.GroupDisableFunction
import i2.keycloak.f2.group.domain.features.command.GroupDisabledEvent
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesCommand
import i2.keycloak.f2.group.domain.features.command.GroupSetAttributesFunction
import i2.keycloak.f2.group.domain.model.GroupModel
import i2.keycloak.f2.user.domain.features.command.UserDisableCommand
import i2.keycloak.f2.user.domain.features.command.UserDisableFunction
import i2.keycloak.f2.user.domain.features.query.UserPageFunction
import i2.keycloak.f2.user.domain.features.query.UserPageQuery
import i2.keycloak.f2.user.domain.model.UserModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupDisableFunctionImpl(
	private val groupSetAttributesFunction: GroupSetAttributesFunction,
	private val userPageFunction: UserPageFunction,
	private val userDisableFunction: UserDisableFunction
) {

	@Bean
	fun groupDisableFunction(): GroupDisableFunction = f2Function { cmd ->
		GroupSetAttributesCommand(
			id = cmd.id,
			attributes = mapOf(GroupModel::enabled.name to "false"),
			realmId = cmd.realmId,
			auth = cmd.auth
		).invokeWith(groupSetAttributesFunction)

		val userIds = UserPageQuery(
			groupId = cmd.id,
			withDisabled = false,
			page = PagePagination(
				page = 0,
				size = Int.MAX_VALUE
			),
			realmId = cmd.realmId,
			auth = cmd.auth
		).invokeWith(userPageFunction)
			.items
			.items
			.map(UserModel::id)

		userIds.forEach { userId ->
			UserDisableCommand(
				id = userId,
				realmId = cmd.realmId,
				auth = cmd.auth
			).invokeWith(userDisableFunction)
		}

		GroupDisabledEvent(
			id = cmd.id,
			userIds = userIds
		)
	}

}
