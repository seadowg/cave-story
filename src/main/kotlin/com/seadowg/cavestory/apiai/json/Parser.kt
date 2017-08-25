package com.seadowg.cavestory.apiai.json

import com.google.gson.Gson
import com.seadowg.cavestory.apiai.Context
import com.seadowg.cavestory.apiai.Request

class Parser {
    fun parse(json: String): Request {
        val jsonRequest = Gson().fromJson(json, JsonRequest::class.java)
        return Request(
                jsonRequest.result.action,
                jsonRequest.result.parameters,
                jsonRequest.result.contexts.map { Context(it.name, it.lifespan) }
        )
    }

    private data class JsonRequest(val result: Result) {
        data class Result(val parameters: Map<String, String>, val contexts: List<JsonContext>, val action: String)
    }

    private data class JsonContext(val name: String, val lifespan: Int)
}