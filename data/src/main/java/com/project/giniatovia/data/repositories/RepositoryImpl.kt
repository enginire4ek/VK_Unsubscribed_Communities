package com.project.giniatovia.data.repositories

import com.project.giniatovia.data.api.VKGetGroupsCommand
import com.project.giniatovia.data.api.VKGetLastPostDateCommand
import com.project.giniatovia.data.api.VKGetMembersCommand
import com.project.giniatovia.data.api.VKLeaveGroupCommand
import com.project.giniatovia.data.models.GroupInfo
import com.project.giniatovia.domain.common.Result
import com.project.giniatovia.domain.entities.BusinessGroupInfo
import com.project.giniatovia.domain.interfaces.ResultCallback
import com.project.giniatovia.domain.repositories.Repository
import com.project.giniatovia.domain.repositories.ResultList
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class RepositoryImpl : Repository {

    override fun getGroups(user_id: Long, callback: ResultCallback<ResultList>) {
        VK.execute(
            VKGetGroupsCommand(user_id),
            object : VKApiCallback<List<GroupInfo>> {

                override fun success(result: List<GroupInfo>) {
                    callback.onResult(
                        Result.Success(
                            result.map {
                                BusinessGroupInfo(
                                    id = it.id,
                                    name = it.name,
                                    photo_100 = it.photo_100,
                                    members_count = it.members_count,
                                    description = it.description
                                )
                            }
                        )
                    )
                }

                override fun fail(error: Exception) {
                    callback.onError(Result.Error(error))
                }
            }
        )
    }

    override fun leaveGroup(group_id: Long, callback: ResultCallback<Int>) {
        VK.execute(
            VKLeaveGroupCommand(group_id),
            object : VKApiCallback<Int> {

                override fun success(result: Int) {
                    callback.onResult(Result.Success(result))
                }

                override fun fail(error: Exception) {
                    callback.onError(Result.Error(error))
                }
            }
        )
    }

    override fun getFriendCount(group_id: Long, callback: ResultCallback<Int>) {
        VK.execute(
            VKGetMembersCommand(group_id),
            object : VKApiCallback<Int> {

                override fun success(result: Int) {
                    callback.onResult(Result.Success(result))
                }

                override fun fail(error: Exception) {
                    callback.onError(Result.Error(error))
                }
            }
        )
    }

    override fun getLastPostDate(owner_id: Long, callback: ResultCallback<String>) {
        VK.execute(
            VKGetLastPostDateCommand(owner_id),
            object : VKApiCallback<String> {

                override fun success(result: String) {
                    callback.onResult(Result.Success(result))
                }

                override fun fail(error: Exception) {
                    callback.onError(Result.Error(error))
                }
            }
        )
    }
}
