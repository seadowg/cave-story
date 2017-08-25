package com.seadowg.cavestory.apiai.json

import com.google.gson.Gson
import com.seadowg.cavestory.apiai.Response

class Serializer {
    fun serialize(response: Response): String {
        return Gson().toJson(JsonResponse(response.prompt, response.contexts.map { JsonContext(it.name, it.requestsToLive) }))
    }

    private data class JsonContext(val name: String, val lifespan: Int)
    private data class JsonResponse(val speech: String, val contextOut: List<JsonContext>)
}
