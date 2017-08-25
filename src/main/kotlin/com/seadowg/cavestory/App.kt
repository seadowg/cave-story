package com.seadowg.cavestory

import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.handler.Fallback
import com.seadowg.cavestory.handler.PerformAction
import com.seadowg.cavestory.system.JvmSystemPort
import com.seadowg.cavestory.apiai.http.Server
import com.seadowg.cavestory.rooms.Cave1
import com.seadowg.cavestory.rooms.Waterfall

fun main(args: Array<String>) {
    App().run()
}

class App {
    private var server = Server()

    fun run() {
        bootServer(server, Script())
    }

    fun kill() {
        server.shutdown()
    }

    private fun bootServer(server: Server, script: Script) {
        server.serve(JvmSystemPort().get(), hashMapOf(
                "perform_action" to PerformAction(script, listOf(
                        Cave1(),
                        Waterfall()
                )),
                "input.unknown" to Fallback()
        ))
    }
}