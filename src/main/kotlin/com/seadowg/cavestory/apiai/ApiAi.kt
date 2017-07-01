package com.seadowg.cavestory.apiai

interface ApiAi {
    fun ask(text: String)
    fun setContext(name: String, requestsToLive: Int)
    fun getArgument(name: String): String?
    fun getContexts(): List<Context>
    fun handleRequest(actionMap: Map<String, Action>)
}

