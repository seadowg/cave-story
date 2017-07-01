import com.seadowg.cavestory.ROOMS
import com.seadowg.cavestory.apiai.Action
import com.seadowg.cavestory.endpoint.Fallback
import com.seadowg.cavestory.endpoint.RoomAction
import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.js.JSApiAiWrapper

external fun require(module:String):dynamic
external val process: dynamic = definedExternally

fun main(args: Array<String>) {
    val httpApp = require("express")()
    httpApp.use(require("body-parser").json())

    httpApp.post("/", { req, res ->
        val JSApiAiApp = require("actions-on-google").ApiAiApp
        val app = JSApiAiWrapper(js("new JSApiAiApp({ request: req, response: res })"))

        val script = Script()
        val actionMap = hashMapOf<String, Action>(
                "input.unknown" to Fallback()
        )

        ROOMS.forEach { actionMap.put(it.name, RoomAction(it, script)) }

        app.handleRequest(actionMap)
    })

    httpApp.listen(process.env.PORT)
}

