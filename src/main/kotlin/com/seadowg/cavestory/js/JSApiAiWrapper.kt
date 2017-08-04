package com.seadowg.cavestory.js

import com.seadowg.cavestory.apiai.*

class JSApiAiWrapper(private val jsApiAiApp: dynamic) {

    fun handleRequest(actionHandlerMap: Map<String, ActionHandler>) {
        val jsMap = js("new Map()")

        actionHandlerMap.forEach { entry ->
            jsMap.set(entry.key, { passedApp: dynamic ->
                val request = Request(ApiAiParams(this), this.getContexts())
                val response = entry.value.handle(request)

                response.contexts.forEach { context ->
                    this.setContext(context.name, context.requestsToLive)
                }

                this.ask(response.prompt)
            })
        }

        jsApiAiApp.handleRequest(jsMap)
    }

    fun getArgument(name: String): String? {
        return jsApiAiApp.getArgument(name) as String?
    }

    fun getContexts(): List<Context> {
        val contexts = mutableListOf<Context>()

        jsApiAiApp.getContexts().forEach { context ->
            contexts.add(Context(context.name, context.lifespan))
        }

        return contexts
    }

    fun ask(text: String) {
        jsApiAiApp.ask(text)
    }

    fun setContext(name: String, requestsToLive: Int) {
        jsApiAiApp.setContext(name, requestsToLive)
    }
}

class ApiAiParams(private val apiAi: JSApiAiWrapper) : Params {
    override fun getArgument(name: String): String? {
        return apiAi.getArgument(name)
    }
}