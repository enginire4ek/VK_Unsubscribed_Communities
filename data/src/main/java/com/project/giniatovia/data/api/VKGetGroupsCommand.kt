package com.project.giniatovia.data.api

import com.project.giniatovia.data.models.Group
import com.project.giniatovia.data.models.GroupInfo
import com.project.giniatovia.data.models.toGroupInfo
import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject

class VKGetGroupsCommand(private val user_id: Long) : ApiCommand<List<GroupInfo>>() {
    override fun onExecute(manager: VKApiManager): List<GroupInfo> {
        val call = VKMethodCall.Builder()
            .method("groups.get")
            .args("user_id", user_id)
            .args("extended", EXTENDED)
            .args("fields", FIELDS)
            .version(manager.config.version)
            .build()
        return manager.execute(call, ResponseApiParser())
    }

    private class ResponseApiParser : VKApiJSONResponseParser<List<GroupInfo>> {
        override fun parse(responseJson: JSONObject): List<GroupInfo> {
            try {
                val response = responseJson.getJSONObject("response")
                val responseItems = response.getJSONArray("items")
                val groups = mutableListOf<GroupInfo>()
                for (i in 0 until responseItems.length()) {
                    val user = Group.parse(responseItems.getJSONObject(i))
                    groups.add(user.toGroupInfo())
                }
                return groups
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    companion object {
        const val EXTENDED = 1
        const val FIELDS = "members_count,description"
        const val NOT_EXTENDED = 0
    }
}
