import com.seadowg.cavestory.apiai.Action
import com.seadowg.cavestory.apiai.ApiAi
import com.seadowg.cavestory.apiai.Context
import com.seadowg.cavestory.engine.Operation
import com.seadowg.cavestory.engine.Room
import com.seadowg.cavestory.rooms.*

external fun require(module:String):dynamic
external val process: dynamic = definedExternally

class JSApiAiWrapper(private val jsApiAiApp: dynamic) : ApiAi {

    override fun handleRequest(actionMap: Map<String, Action>) {
        val jsMap = js("new Map()")

        actionMap.forEach { entry ->
            jsMap.set(entry.key, { passedApp: dynamic ->
                entry.value.handle(JSApiAiWrapper(passedApp))
            })
        }

        jsApiAiApp.handleRequest(jsMap)
    }

    override fun getArgument(name: String): String? {
        return jsApiAiApp.getArgument(name) as String?
    }

    override fun getContexts(): List<Context> {
        val contexts = mutableListOf<Context>()

        jsApiAiApp.getContexts().forEach { context ->
            contexts.add(Context(context.name))
        }

        return contexts
    }

    override fun ask(text: String) {
        jsApiAiApp.ask(text)
    }

    override fun setContext(name: String, requestsToLive: Int) {
        jsApiAiApp.setContext(name, requestsToLive)
    }
}

class Fallback : Action {
    override fun handle(apiAi: ApiAi) {
        apiAi.getContexts().forEach { context ->
            apiAi.setContext(context.name, 1)
        }

        apiAi.ask("I don't understand. Why don't you try looking around?")
    }
}

class RoomAction(private val room: Room): Action {
    override fun handle(apiAi: ApiAi) {
        val nextState = room.perform(Operation(
                apiAi.getArgument("action")!!,
                apiAi.getArgument("thing")
        ))

        apiAi.setContext("in_" + nextState.room.name, 1)
        apiAi.ask(nextState.prompt)
    }
}

fun main(args: Array<String>) {
    val httpApp = require("express")()
    httpApp.use(require("body-parser").json())

    httpApp.post("/", { req, res ->
        val JSApiAiApp = require("actions-on-google").ApiAiApp
        val app = JSApiAiWrapper(js("new JSApiAiApp({ request: req, response: res })"))

        app.handleRequest(mapOf(
                "cave_1" to RoomAction(Cave1()),
                "waterfall" to RoomAction(Waterfall()),
                "input.unknown" to Fallback()
        ))
    })

    httpApp.listen(process.env.PORT)
}

