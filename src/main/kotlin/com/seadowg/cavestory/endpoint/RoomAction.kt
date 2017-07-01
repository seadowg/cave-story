package com.seadowg.cavestory.endpoint

import com.seadowg.cavestory.apiai.Action
import com.seadowg.cavestory.apiai.ApiAi
import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.engine.Script


class RoomAction(private val room: Room, private val script: Script): Action {
    override fun handle(apiAi: ApiAi) {
        val nextState = room.perform(Operation(
                apiAi.getArgument("action")!!,
                apiAi.getArgument("thing")
        ), script)

        apiAi.setContext("in_" + nextState.room.name, 1)
        apiAi.ask(nextState.prompt)
    }
}