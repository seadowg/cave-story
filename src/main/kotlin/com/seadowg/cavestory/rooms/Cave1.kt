package com.seadowg.cavestory.rooms

import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.engine.State

class Cave1 : Room {

    override val name = "cave_1"

    override fun perform(operation: Operation): State {
        return if (operation.action == "look around" && operation.thing == null) {
            State(this, "The cave is very dark but you can make out some small rocks " +
                    "scattered around the floor. Behind you you can just make out some light in " +
                    "the distance. You can also here the sound of running water in the opposite direction")
        } else if (operation.action == "move" && operation.thing == "light") {
            State(this, "You run over to the light hoping to find a way out of the cave but " +
                    "you find a shaft of light descending from a roof that must be 30 or 40 foot up. How are you going " +
                    "to get out of here?")
        } else if (operation.action == "pick up" && operation.thing == "rock") {
            State(this, "You pick up a rock and put in your bag.")
        } else if (operation.action == "move" && operation.thing == "water") {
            State(Waterfall(), "You walk towards the sound of water. You walk through a narrow tunnel for several " +
                    "minutes and then emerge in a large chamber with a giant waterfall.")
        } else {
            State(this, "You try that and it doesn\'t work.")
        }
    }
}