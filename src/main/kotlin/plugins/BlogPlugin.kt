package plugins

import blog.addBlogPages
import blog.loadArticles
import common.*
import common.OutputContext
import kotlinx.html.*
import utils.createDirectory

/**
 * Gets Markdown files from resources and transforms each into an article page and merges them into an index page
 * TODO: Parameter blog folder
 * TODO: Parameter output
 * TODO: Parameter template
 * TODO: Index only with titles and separate page for full text search
 * TODO: Use generic mf2 Resource instead of ArticleResource
 * TODO: Supported Langs and default lang
 */
public class BlogPlugin(
    private val blogIndexTitlesAndDescriptions: (Language) -> TitlesAndDescriptions,
    override val enabled: Boolean = true
) : WebPlugin {
    context(EnvContext, OutputContext)
    override fun pages(): List<Page> {
        for (language in languages) {
            createDirectory("deploy/output${language.langPath}blog")
        }
        val articles = languages.associateWith { loadArticles("/${it.locale}/blog") }

        return buildList {
            articles.forEach { (lang, articles) ->
                with(LanguageContext(lang)) {
                    addBlogPages(articles, blogIndexTitlesAndDescriptions(lang))
                }
            }
        }
    }

    context(EnvContext, LanguageContext, PageContext, NAV)
    override fun navTags() {
        with(this@NAV) {
            a {
                if (path == "" || path.contains("blog")) {
                    classes = classes + "selected"
                    if (isIndex) classes = classes + "u-url"
                }
                href = language.langPath
            +"Blog"
            }
            +" "
        }
    }
}

internal class ArticleResource(name: String, unparsedContent: String) {
    val id: Int = name.split("$")[0].toInt()
    val blogId: String
    val titlesAndDescriptions: TitlesAndDescriptions
    val createdDate: String
    val modifiedDate: String
    val rawContent: String

    init {
        val unparsedContentLines = unparsedContent.lines()
        blogId = unparsedContentLines[0]
        titlesAndDescriptions = TitlesAndDescriptions(
            unparsedContentLines[1],
            unparsedContentLines[2],
            unparsedContentLines[3],
            unparsedContentLines[4],
        )
        createdDate = unparsedContentLines[5]
        modifiedDate = unparsedContentLines[6]
        rawContent = unparsedContentLines.subList(7, unparsedContentLines.size).joinToString("\n")
    }
}
