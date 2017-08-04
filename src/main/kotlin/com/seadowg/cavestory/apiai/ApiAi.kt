package com.seadowg.cavestory.apiai

interface ApiAi {
    fun ask(text: String)
    fun setContext(name: String, requestsToLive: Int)
    fun getArgument(name: String): String?
    fun getContexts(): List<Context>
    fun handleRequest(actionMap: Map<String, Action>)
}

class ApiAiParams(private val apiAi: ApiAi) : Params {
    override fun getArgument(name: String): String? {
        return apiAi.getArgument(name)
    }
}

interface Params {
    fun getArgument(name: String): String?
}

