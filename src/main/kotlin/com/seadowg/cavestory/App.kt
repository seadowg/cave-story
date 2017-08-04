import com.seadowg.cavestory.handler.Fallback
import com.seadowg.cavestory.handler.Perform
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
        val actionMap = hashMapOf(
                "perform_action" to Perform(script),
                "input.unknown" to Fallback()
        )

        app.handleRequest(actionMap)
    })

    httpApp.listen(process.env.PORT)
}

