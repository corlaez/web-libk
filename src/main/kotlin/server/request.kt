package server

import org.eclipse.jetty.http.HttpStatus
import java.net.HttpURLConnection
import java.net.URL

internal fun httpGet(urlStr: String): Pair<Int, String> {
    val url = URL(urlStr)
    val con = url.openConnection() as HttpURLConnection
    con.requestMethod = "GET"
    return runCatching {
        con.responseCode to con.inputStream.bufferedReader().readText()
    }.getOrDefault(HttpStatus.INTERNAL_SERVER_ERROR_500 to "Couldn't connect")
}
