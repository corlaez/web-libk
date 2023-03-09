package server

import common.EnvContext
import common.logger
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import io.javalin.websocket.WsContext
import java.time.LocalTime

context(EnvContext)
internal fun serve() {
    if (arg.isRelease()) return
    val app = Javalin.create { config ->
        config.showJavalinBanner = false
        config.addStaticFiles { staticFiles ->
            staticFiles.directory = "deploy/output"
            staticFiles.location = Location.EXTERNAL
        }
        config.enableDevLogging()
    }.start(port.toInt())
    // Expose arguments used to run this server for later regeneration
    app.get("/dev/arg") { ctx ->
        ctx.result(arg.toString())
    }
    devConfig(app)
    logger.info("Listening on http://localhost:$port/")
    if (arg.isPrd()) logger.info("This server does not support hot reloading")
}

context(EnvContext)
private fun devConfig(app: Javalin) {
    if (arg.isDev()) {
        val wsContexts = mutableListOf<WsContext>()
        app.ws("/dev/subscribe") { ws ->
            ws.onConnect { wsContexts += it }
            ws.onClose { ctx -> wsContexts.remove(ctx) }
        }
        app.get("/dev/reload") { ctx ->
            wsContexts.forEach { it.send("reload") }
            val message = "${wsContexts.size} clients Reloaded! ${LocalTime.now()}"
            logger.info(message)
            ctx.result(message)
        }
    }
}

// Avoid cache https://reqbin.com/req/doog8aai/http-headers-to-prevent-caching
//            if (arg.isDev())
//                staticFiles.headers = mapOf(
//                    "Cache-Control" to "no-cache, no-store, must-revalidate",
//                    "Pragma" to "no-cache",
//                    "Expires" to "0",
//                )
//            if (arg.isProd())
//                staticFiles.headers = mapOf(
//                    "Cache-Control" to "max-age=2",
//                )
