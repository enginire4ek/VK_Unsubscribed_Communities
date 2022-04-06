package com.project.giniatovia.domain.repositories

import com.project.giniatovia.domain.entities.BusinessGroupInfo
import com.project.giniatovia.domain.interfaces.ResultCallback

typealias ResultList = List<BusinessGroupInfo>

interface Repository {
    fun getGroups(user_id: Long, callback: ResultCallback<ResultList>)
    fun leaveGroup(group_id: Long, callback: ResultCallback<Int>)
    fun getFriendCount(group_id: Long, callback: ResultCallback<Int>)
    fun getLastPostDate(owner_id: Long, callback: ResultCallback<String>)
}
