package com.seadowg.cavestory.js

import com.seadowg.cavestory.apiai.ActionHandler
import com.seadowg.cavestory.apiai.Server

external fun require(module:String):dynamic

class NodeServer : Server {

    override fun serve(port: Int, actionHandlers: Map<String, ActionHandler>) {
        val httpApp = require("express")()
        val morganBody = require("morgan-body")

        httpApp.use(require("body-parser").json())
        morganBody(httpApp)

        httpApp.post("/", { req, res ->
            val JSApiAiApp = require("actions-on-google").ApiAiApp
            val app = JSApiAiWrapper(js("new JSApiAiApp({ request: req, response: res })"))
            app.handleRequest(actionHandlers)
        })

        httpApp.listen(port)
    }
}
