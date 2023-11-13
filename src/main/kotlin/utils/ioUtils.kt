package utils

import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.*

// Check https://stackoverflow.com/questions/10247161/when-to-use-getresourceasstream-method/10247343#10247343
internal fun getUri(name: String): URI =
    "".javaClass.getResource(name)?.toURI() ?:
    listOf("main", "test").map { File("src/$it/resources$name") }
        .firstOrNull() { it.isFile || it.isDirectory }
        ?.toURI()?: error("File $name not found")

internal fun listFilenamesInDirectory(name: String): List<String> {
    val dirPath = Paths.get(getUri(name))
    val paths = Files.list(dirPath)
    return paths
        .map { p -> p.fileName.toString() }
        .collect(Collectors.toList())
}

// Check https://stackoverflow.com/questions/10247161/when-to-use-getresourceasstream-method/10247343#10247343
internal fun loadResourceAsBytes(name: String) =
    // From Jar
    "".javaClass.getResourceAsStream(name)?.readBytes() ?:
    // from Filesystem
    runCatching { FileInputStream("src/main/resources$name").readBytes() }
        .getOrElse { FileInputStream("src/test/resources$name").readBytes() }
        
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
