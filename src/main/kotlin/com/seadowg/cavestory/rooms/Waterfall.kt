package com.seadowg.cavestory.rooms

import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.engine.State

class Waterfall : Room {

    override val name = "waterfall"

    override fun perform(operation: Operation): State {
        return if (operation.action == "look around" && operation.thing == null) {
            State(this, "Oh look a waterfall!")
        } else if (operation.action == "wash" && operation.thing == "waterfall") {
            State(this, "You emerge from the waterfall sparkling clean. This changes nothing.")
        } else {
            State(this, "You try that and it doesn\'t work.")
        }
    }
}
