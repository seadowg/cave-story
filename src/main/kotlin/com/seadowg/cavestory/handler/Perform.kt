package com.seadowg.cavestory.handler

import com.seadowg.cavestory.apiai.*
import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.rooms.Cave1
import com.seadowg.cavestory.rooms.Waterfall

class Perform(private val script: Script) : ActionHandler {
    override fun handle(request: Request): Response {
        val currentLocation = request.contexts.singleOrNull { it.name.startsWith("in_") } ?: Context("in_cave_1", 1)
        val room = ROOMS.single { "in_${it.name}" == currentLocation.name }

        val state = room.perform(Operation(
                request.params.getArgument("action")!!,
                request.params.getArgument("thing")
        ), script)

        return Response(state.prompt, listOf(Context("in_" + state.room.name, 1)))
    }

    private val ROOMS = listOf(
            Cave1(),
            Waterfall()
    )
}
