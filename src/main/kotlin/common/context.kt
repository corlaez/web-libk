package common

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML
import kotlinx.html.stream.createHTML
import md.MarkdownSupport
import server.Arg

public data class EnvText(
    val author: (Language) -> String,
    val logoAlly: (Language) -> String?,
    val LOGO_SQR_THEME_RGB: String?,
    val LOGO_SQR_IMAGE_PATH: String?,
    val WEBSITE_NAME: String,// could be derived of lang
    val TWITTER_HANDLE: String?,
    val SERVICE_WORKER_JS_PATH: String?,
    val EXTERNAL_RELS: String = "nofollow noreferrer noopener",
)

public data class EnvContext(
    val arg: Arg,
    val port: String,
    val productionDomain: String,
    val envText: EnvText,
    val languages: List<Language>,
    val webPlugins: List<WebPlugin>,
) {
    init {
        envText.apply {
            WebVariables.register("WEBSITE_NAME" to WEBSITE_NAME)
            WebVariables.register("EXTERNAL_RELS" to EXTERNAL_RELS)
            LOGO_SQR_THEME_RGB?.let { WebVariables.register("LOGO_SQR_THEME_RGB" to LOGO_SQR_THEME_RGB) }
            LOGO_SQR_IMAGE_PATH?.let { WebVariables.register("LOGO_SQR_IMAGE_PATH" to LOGO_SQR_IMAGE_PATH) }
            TWITTER_HANDLE?.let { WebVariables.register("TWITTER_HANDLE" to TWITTER_HANDLE) }
            SERVICE_WORKER_JS_PATH?.let { WebVariables.register("SERVICE_WORKER_JS_PATH" to SERVICE_WORKER_JS_PATH) }
        }
    }

    val domain: String = when(arg.isPrd()) {
        true -> productionDomain
        false -> "http://localhost:$port"
    }
    // prettyPrint helps people to read and understand the source in their browsers and doesn't do much to reduce size
    internal val prettyPrint = true
    private val markdownSupport = MarkdownSupport(webPlugins)

    internal fun mdToHtml(inputMarkdown: String) = markdownSupport.mdToHtml(inputMarkdown)
    public fun Appendable.h(): TagConsumer<Appendable> = appendHTML(prettyPrint = prettyPrint)
    public fun createH(): TagConsumer<String> = createHTML(prettyPrint = prettyPrint)
}

public data class OutputContext(val resources: Resources)

public data class LanguageContext(val language: Language) {
    val t: LocalizedText = LocalizedText(language)
}

context(EnvContext, LanguageContext)
public class PageContext(
    public val fileName: String,
    public val pageOgType: String,
    public val titlesAndDescriptions: TitlesAndDescriptions,
    public  val activePlugin: List<String> = emptyList()
) {
    public val isIndex: Boolean = fileName.split("/").last() == "index.html"
    public val path: String = if (isIndex) fileName.split("/").dropLast(1).joinToString("/")  else fileName
    public val pageUrl: String get() = domain + language.langPath + path
}
