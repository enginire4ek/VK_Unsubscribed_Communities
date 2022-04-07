package com.project.giniatovia.domain.repositories

import com.project.giniatovia.domain.entities.BusinessGroupInfo
import com.project.giniatovia.domain.interfaces.ResultCallback

typealias ResultList = List<BusinessGroupInfo>

interface GroupsRepository {
    val groupsCollection: ResultList

    fun getGroups(userId: Long, callback: ResultCallback<ResultList>)
    fun leaveGroup(groupId: Long, callback: ResultCallback<Int>)
    fun getFriendCount(groupId: Long, callback: ResultCallback<Int>)
    fun getLastPostDate(ownerId: Long, callback: ResultCallback<String>)
    fun saveGroupsCollection(groups: ResultList)
    fun restoreGroupsCollection(): ResultList
}
