package com.seadowg.cavestory.rooms

import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.engine.State

class Waterfall : Room {

    override val name = "waterfall"

    override fun perform(operation: Operation, script: Script): State {
        return if (operation.action == "look around" && operation.thing == null) {
            State(this, script.ohLookAWaterfall)
        } else if (operation.action == "wash" && operation.thing == "waterfall") {
            State(this, script.emergeFromTheWaterfall)
        } else {
            State(this, script.doesntWork)
        }
    }
}
