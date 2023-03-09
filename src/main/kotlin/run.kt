import common.EnvContext
import common.logger
import generate.generate
import org.eclipse.jetty.http.HttpStatus
import server.Arg
import server.httpGet
import server.serve
import java.time.LocalDateTime

context(EnvContext)
public fun run() {
    if (arg.isRegenerate()) {
        val serverArg = getRequestArg(port)
        with(EnvContext(serverArg, port, productionDomain, envText, languages, webPlugins)) {
            generate()
            devGetRequestReload()
        }
    } else {
        with(EnvContext(arg, port, productionDomain, envText, languages,  webPlugins)) {
            generate()
            serve()
        }
    }
}

private fun getRequestArg(port: String): Arg {
    val (argStatus, argResponse) = httpGet("http://localhost:$port/dev/arg")
    return if (argStatus != HttpStatus.OK_200) {
        logger.warn("server failed to provide arg (generating as prd). Code: $argStatus")
        Arg.prd
    } else {
        Arg.valueOf(argResponse)
    }
}

context(EnvContext)
private fun devGetRequestReload() {
    val reloadScriptPresentInWebClient = arg.isDev()
    if (reloadScriptPresentInWebClient) {
        val (reloadStatus, reloadResponse) = httpGet("http://localhost:$port/dev/reload")
        if (reloadStatus != HttpStatus.OK_200) error("dev server failed to reload clients. Code: $reloadStatus")
        logger.info("$reloadResponse ${LocalDateTime.now()}")
    }
}
