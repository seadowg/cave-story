package com.seadowg.cavestory

import com.seadowg.cavestory.apiai.Server
import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.handler.Fallback
import com.seadowg.cavestory.handler.Perform
import com.seadowg.cavestory.jvm.JvmSystemPort
import com.seadowg.cavestory.jvm.SparkServer
import com.seadowg.cavestory.system.EnvPort

fun main(args: Array<String>) {
    App().run()
}

class App {
    fun run() {
        bootServer(SparkServer(), JvmSystemPort(), Script())
    }

    fun kill() {

    }

    private fun bootServer(server: Server, envPort: EnvPort, script: Script) {
        server.serve(envPort.get(), hashMapOf(
                "perform_action" to Perform(script),
                "input.unknown" to Fallback()
        ))
    }
}