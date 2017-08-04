package com.seadowg.cavestory.endpoint

import com.seadowg.cavestory.apiai.Context
import com.seadowg.cavestory.apiai.Request
import com.seadowg.cavestory.apiai.Response

class Fallback : com.seadowg.cavestory.apiai.Action {
    override fun handle(request: Request): Response {
        val location = request.contexts.firstOrNull { context -> context.name.startsWith("in_") }

        return if (location == null) {
            Response("I don't understand. Why don't you try looking around?", request.contexts + listOf(Context("in_cave_1", 1)))
        } else {
            Response("I don't understand. Why don't you try looking around?", request.contexts)
        }
    }
}
