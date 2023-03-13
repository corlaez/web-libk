package generate

import common.WebVariables.replaceWebVariables
import common.EnvContext
import common.OutputContext
import common.Page
import common.Resources
import utils.*

private data class Output(
    val pages: List<Page>,
    val staticDir: String,
)

context(EnvContext)
internal fun generate() {
    val resources = Resources(
        sytlesCss = loadAndMergeCss().replaceWebVariables(),
        faviconTags = loadResourceAsString("/tags.txt").replace(">\n", ">").replaceWebVariables(),
        manifestJson = loadResourceAsString("/manifest.json").replaceWebVariables(),
        browserconfigXml = loadResourceAsString("/browserconfig.xml").replaceWebVariables(),
    )
    deleteDirectory("deploy/output")
    val output = with(OutputContext(resources)) {
        Output(
            pages = buildList {
                add(Page("styles.css", "/", resources.sytlesCss))
                add(Page("manifest.json", "/", resources.manifestJson))
                add(Page("browserconfig.xml", "/", resources.browserconfigXml))
                webPlugins.forEach { addAll(it.pages()) }
            },
            staticDir = "static"
        )
    }
    output.pages.forEach { saveFile(it.content, "deploy/output${it.namespace}", it.name) }
    copyDirectory(output.staticDir, "deploy/output")
}

context(EnvContext)
private fun loadAndMergeCss(): String {
    return buildList {
        add(loadResourceAsString("/css/print.min.css"))
        add(loadResourceAsString("/css/fluidity.min.css"))
        add(loadResourceAsString("/css/modest-variation.css").minifyCss())
        add(loadResourceAsString("/css/fire.css").minifyCss())
    }.joinToString("")
}
