package com.seadowg.cavestory.jvm

import com.google.gson.Gson
import com.seadowg.cavestory.apiai.*
import spark.kotlin.Http
import spark.kotlin.ignite

class SparkServer : Server {

    private var http: Http? = null

    override fun serve(port: Int, actionHandlers: Map<String, ActionHandler>) {
        val spark = ignite().port(port)

        spark.post("/") {
            val gson = Gson()
            val jsonRequest = gson.fromJson(request.body(), JsonRequest::class.java)

            val apiAiRequest = Request(jsonRequest.result.parameters, jsonRequest.result.contexts.map { Context(it.name, it.lifespan) })
            val apiAiResponse = actionHandlers[jsonRequest.result.action]!!.handle(apiAiRequest)

            response.header("Content-Type", "application/json")
            gson.toJson(JsonResponse(apiAiResponse.prompt, apiAiResponse.contexts.map { JsonContext(it.name, it.requestsToLive) }))
        }

        http = spark
    }

    override fun shutdown() {
        http?.stop()
    }
}

private data class JsonContext(val name: String, val lifespan: Int)

private data class JsonRequest(val result: Result) {
    data class Result(val parameters: Map<String, String>, val contexts: List<JsonContext>, val action: String)
}


private data class JsonResponse(val speech: String, val contextOut: List<JsonContext>)