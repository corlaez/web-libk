package common

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
    val domain: String = when(arg.isPrd()) {
        true -> productionDomain
        false -> "http://localhost:$port"
    }
    // prettyPrint helps people to read and understand the source in their browsers and doesn't do much to reduce size
    internal val prettyPrint = true
    private val markdownSupport = MarkdownSupport(webPlugins)

    internal fun mdToHtml(inputMarkdown: String) = markdownSupport.mdToHtml(inputMarkdown)
    internal fun Appendable.h() = appendHTML(prettyPrint = prettyPrint)
    internal fun createH() = createHTML(prettyPrint = prettyPrint)
}

internal data class OutputContext(val resources: Resources)

internal data class LanguageContext(val language: Language) {
    val t: LocalizedText = LocalizedText(language)
}

context(EnvContext, LanguageContext)
internal class PageContext(
    val fileName: String,
    val pageOgType: String,
    val titlesAndDescriptions: TitlesAndDescriptions,
    val activePlugin: List<String> = emptyList()
) {
    val isIndex = fileName.split("/").last() == "index.html"
    val path = if (isIndex) fileName.split("/").dropLast(1).joinToString("/")  else fileName
    val pageUrl get() = domain + language.langPath + path
}
