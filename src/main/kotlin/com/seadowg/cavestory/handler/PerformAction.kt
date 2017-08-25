package com.seadowg.cavestory.handler

import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.engine.Script
import com.seadowg.dave.ActionHandler
import com.seadowg.dave.Context
import com.seadowg.dave.Request
import com.seadowg.dave.Response

class PerformAction(private val script: Script, private val rooms: List<Room> = emptyList()) : ActionHandler {
    override fun handle(request: Request): Response {
        val currentLocation = request.contexts.single { it.name.startsWith("in_") }
        val room = rooms.single { "in_${it.name}" == currentLocation.name }

        val state = room.perform(Operation(
                request.params["action"]!!,
                request.params["thing"]
        ), script)

        return Response(state.prompt, listOf(Context("in_" + state.room.name, 1)))
    }
}
