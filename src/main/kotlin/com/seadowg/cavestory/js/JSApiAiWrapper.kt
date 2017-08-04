package com.seadowg.cavestory.js

import com.seadowg.cavestory.apiai.*

class JSApiAiWrapper(private val jsApiAiApp: dynamic) : ApiAi {

    override fun handleRequest(actionMap: Map<String, Action>) {
        val jsMap = js("new Map()")

        actionMap.forEach { entry ->
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

    override fun getArgument(name: String): String? {
        return jsApiAiApp.getArgument(name) as String?
    }

    override fun getContexts(): List<Context> {
        val contexts = mutableListOf<Context>()

        jsApiAiApp.getContexts().forEach { context ->
            contexts.add(Context(context.name, context.lifespan))
        }

        return contexts
    }

    override fun ask(text: String) {
        jsApiAiApp.ask(text)
    }

    override fun setContext(name: String, requestsToLive: Int) {
        jsApiAiApp.setContext(name, requestsToLive)
    }
}