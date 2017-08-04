package com.seadowg.cavestory.apiai

interface Server {
    fun serve(port: Int, actionHandlers: Map<String, ActionHandler>)
}
