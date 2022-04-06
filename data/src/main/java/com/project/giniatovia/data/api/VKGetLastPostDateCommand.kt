package com.project.giniatovia.data.api

import com.project.giniatovia.data.common.DateManager
import com.vk.api.sdk.VKApiJSONResponseParser
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject

class VKGetLastPostDateCommand(private val owner_id: Long) : ApiCommand<String>() {
    override fun onExecute(manager: VKApiManager): String {
        val call = VKMethodCall.Builder()
            .method("wall.get")
            .args("owner_id", owner_id)
            .args("count", POSTS_COUNT)
            .version(manager.config.version)
            .build()
        return manager.execute(call, ResponseApiParser())
    }

    private inner class ResponseApiParser : VKApiJSONResponseParser<String> {
        override fun parse(responseJson: JSONObject): String {
            try {
                val response = responseJson.getJSONObject("response")
                val responsePosts = response.getJSONArray("items")
                if ((responsePosts.length() > 2) || (responsePosts.length() == 0)) {
                    return "не найдена"
                }
                val date = DateManager.formatDate(
                    responsePosts.getJSONObject(0).getLong("date"),
                    responsePosts.getJSONObject(1).getLong("date")
                )
                return date
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    companion object {
        const val POSTS_COUNT = 2
    }
}
