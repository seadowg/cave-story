package com.seadowg.cavestory.rooms

import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.engine.State

class Cave1 : Room {

    override val name = "cave_1"

    override fun perform(operation: Operation, script: Script): State {
        return if (operation.action == "look around" && operation.thing == null) {
            State(this, script.theCaveIsVeryDark)
        } else if (operation.action == "move" && operation.thing == "light") {
            State(this, script.youRunToTheLight)
        } else if (operation.action == "pick up" && operation.thing == "rock") {
            State(this, script.youPickUpTheRock)
        } else if (operation.action == "move" && operation.thing == "water") {
            State(Waterfall(), script.youWalkTowardsTheSoundOfWater)
        } else {
            State(this, script.doesntWork)
        }
    }
}