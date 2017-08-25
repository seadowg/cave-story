package com.seadowg.cavestory.handler

import com.seadowg.dave.ActionHandler
import com.seadowg.dave.Context
import com.seadowg.dave.Request
import com.seadowg.dave.Response

class Fallback : ActionHandler {
    override fun handle(request: Request): Response {
        val location = request.contexts.firstOrNull { context -> context.name.startsWith("in_") }

        return if (location == null) {
            Response("I don't understand. Why don't you try looking around?", request.contexts + listOf(Context("in_cave_1", 1)))
        } else {
            Response("I don't understand. Why don't you try looking around?", request.contexts)
        }
    }
}
