import com.seadowg.cavestory.engine.Script
import com.seadowg.cavestory.handler.Fallback
import com.seadowg.cavestory.handler.Perform
import com.seadowg.cavestory.js.NodeServer

external val process: dynamic = definedExternally

fun main(args: Array<String>) {
    val script = Script()

    NodeServer().serve(process.env.PORT, hashMapOf(
            "perform_action" to Perform(script),
            "input.unknown" to Fallback()
    ))
}

