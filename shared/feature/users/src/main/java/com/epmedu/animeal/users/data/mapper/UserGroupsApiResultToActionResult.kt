package com.epmedu.animeal.users.data.mapper

import com.epmedu.animeal.common.data.wrapper.ApiResult
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.users.data.model.UserGroups as DataUserGroups
import com.epmedu.animeal.users.domain.model.UserGroup as DomainUserGroup
import com.epmedu.animeal.users.domain.model.UserGroup

internal fun ApiResult<DataUserGroups>.toActionResult(): ActionResult<List<DomainUserGroup>> {
    return when (this) {
        is ApiResult.Success -> {
            ActionResult.Success(
                data.userGroups.mapNotNull { group ->
                    UserGroup.entries.find { it.name == group.name }
                }
            )
        }

        is ApiResult.Failure -> {
            ActionResult.Failure(error)
        }
    }
}