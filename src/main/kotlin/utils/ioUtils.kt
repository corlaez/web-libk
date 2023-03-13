package utils

import java.io.File
import java.net.URI
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.*

internal fun getUri(name: String): URI {
    val url = "".javaClass.getResource(name)
    return url!!.toURI()
}
internal fun listFilenamesInDirectory(name: String): List<String> {
    val dirPath = Paths.get(getUri(name))
    val paths = Files.list(dirPath)
    return paths
        .map { p -> p.fileName.toString() }
        .collect(Collectors.toList())
}
internal fun loadResourceAsBytes(name: String) = "".javaClass.getResourceAsStream(name)!!.readBytes()
internal fun loadResourceAsString(name: String, charset: Charset = Charset.forName("UTF-8")) =
    String(loadResourceAsBytes(name), charset)

internal fun saveFile(content: String, folder: String?, name: String) {
    if (folder != null) createDirectory(folder)
    File("$folder$name").writeText(content)
}
internal fun copyDirectory(name: String, target: String) = File(name).copyRecursively(File(target), true)

internal fun deleteDirectory(name: String) = File(name).deleteRecursively()
public fun createDirectory(name: String): Boolean = File(name).mkdirs()

internal fun saveFile(content: ByteArray, folder: String?, name: String) {
    if (folder != null) File(folder).mkdirs()
    File("$folder/$name").writeBytes(content)
}
internal fun saveFileFromResource(resourceName: String, folder: String?, name: String) {
    saveFile(loadResourceAsString(resourceName), folder, name)
}
