package com.seadowg.cavestory.apiai.http

import com.seadowg.cavestory.apiai.ActionHandler
import com.seadowg.cavestory.apiai.RequestRouter
import com.seadowg.cavestory.apiai.json.Parser
import com.seadowg.cavestory.apiai.json.Serializer
import spark.kotlin.Http
import spark.kotlin.ignite

class Server {

    private var http: Http? = null

    fun serve(port: Int, actionHandlers: Map<String, ActionHandler>) {
        val spark = ignite().port(port)

        val requestRouter = RequestRouter(actionHandlers)
        val parser = Parser()
        val serializer = Serializer()

        spark.post("/") {
            val apiAiRequest = parser.parse(request.body())
            val apiAiResponse = requestRouter.route(apiAiRequest)

            response.header("Content-Type", "application/json")
            serializer.serialize(apiAiResponse)
        }

        http = spark
    }

    fun shutdown() {
        http?.stop()
    }
}