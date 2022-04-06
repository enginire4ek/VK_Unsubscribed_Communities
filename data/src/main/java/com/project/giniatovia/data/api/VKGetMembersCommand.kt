package com.project.giniatovia.data.api

import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject

class VKGetMembersCommand(private val group_id: Long) : ApiCommand<Int>() {
    override fun onExecute(manager: VKApiManager): Int {
        val call = VKMethodCall.Builder()
            .method("groups.getMembers")
            .args("group_id", group_id)
            .args("filter", FILTER)
            .version(manager.config.version)
            .build()
        return manager.execute(call, ResponseApiParser())
    }

    private class ResponseApiParser : VKApiJSONResponseParser<Int> {
        override fun parse(responseJson: JSONObject): Int {
            try {
                val response = responseJson.getJSONObject("response")
                return response.getInt("count")
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    companion object {
        const val FILTER = "friends"
    }
}
