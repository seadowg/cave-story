package com.seadowg.cavestory.engine

import com.seadowg.cavestory.Script

interface Room {
    val name: String
    fun perform(operation: Operation, script: Script): State
}