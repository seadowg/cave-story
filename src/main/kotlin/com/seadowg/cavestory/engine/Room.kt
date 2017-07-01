package com.seadowg.cavestory.engine

interface Room {
    val name: String
    fun perform(operation: Operation, script: Script): State
}