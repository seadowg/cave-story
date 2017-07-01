package com.seadowg.cavestory.js

import com.seadowg.cavestory.apiai.Action
import com.seadowg.cavestory.apiai.ApiAi
import com.seadowg.cavestory.apiai.Context

class JSApiAiWrapper(private val jsApiAiApp: dynamic) : ApiAi {

    override fun handleRequest(actionMap: Map<String, Action>) {
        val jsMap = js("new Map()")

        actionMap.forEach { entry ->
            jsMap.set(entry.key, { passedApp: dynamic ->
                entry.value.handle(JSApiAiWrapper(passedApp))
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
            contexts.add(Context(context.name))
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