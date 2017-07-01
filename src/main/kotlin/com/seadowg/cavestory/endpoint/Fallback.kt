package com.seadowg.cavestory.endpoint

class Fallback : com.seadowg.cavestory.apiai.Action {
    override fun handle(apiAi: com.seadowg.cavestory.apiai.ApiAi) {
        apiAi.getContexts().forEach { context ->
            apiAi.setContext(context.name, 1)
        }

        val location = apiAi.getContexts().firstOrNull { context -> context.name.startsWith("in_") }

        if (location == null) {
            apiAi.setContext("in_cave_1", 1)
        }

        apiAi.ask("I don't understand. Why don't you try looking around?")
    }
}
