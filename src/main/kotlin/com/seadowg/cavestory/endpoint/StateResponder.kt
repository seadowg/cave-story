package com.seadowg.cavestory.endpoint

import com.seadowg.cavestory.apiai.ApiAi
import com.seadowg.cavestory.engine.State


class StateResponder(private val apiAi: ApiAi) {
    fun respond(state: State) {
        apiAi.setContext("in_" + state.room.name, 1)
        apiAi.ask(state.prompt)
    }
}