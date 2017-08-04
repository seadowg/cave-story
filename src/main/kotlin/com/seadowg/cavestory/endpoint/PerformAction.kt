package com.seadowg.cavestory.endpoint

import com.seadowg.cavestory.ROOMS
import com.seadowg.cavestory.apiai.Action
import com.seadowg.cavestory.apiai.ApiAi
import com.seadowg.cavestory.apiai.Context
import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Script

class PerformAction(private val script: Script) : Action {
    override fun handle(apiAi: ApiAi) {
        val currentLocation = apiAi.getContexts().singleOrNull { it.name.startsWith("in_") } ?: Context("in_cave_1")
        val room = ROOMS.single { "in_${it.name}" == currentLocation.name }

        val state = room.perform(Operation(
                apiAi.getArgument("action")!!,
                apiAi.getArgument("thing")
        ), script)

        StateResponder(apiAi).respond(state)
    }
}
