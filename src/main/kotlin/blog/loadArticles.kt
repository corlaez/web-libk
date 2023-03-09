package blog

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*
import plugins.MermaidPlugin
import templates.asHtmlPage
import utils.listFilenamesInDirectory
import utils.loadResourceAsString

internal fun loadArticles(folder: String): List<ArticleResource> {
    return listFilenamesInDirectory(folder)
        .filter { it.contains("$") }
        .map { ArticleResource(it, loadResourceAsString("$folder/$it")) }
        .sortedByDescending { it.id }
}

context(EnvContext, LanguageContext, OutputContext)
internal fun MutableList<Page>.addBlogPages(
    articles: List<ArticleResource>,
    blogIndexTitlesAndDescriptions: TitlesAndDescriptions
) {
    // todo p-category
    val mergedArticles = articles.joinToString("") { articleResource ->
        buildString {
            h().details(classes = "h-entry") {
                summary(classes = "h2") {
                    h2 {
                        a(classes = "u-url p-name") {
                            href = "${language.langPath}blog/${articleResource.blogId}"
                            +articleResource.titlesAndDescriptions.visibleTitle!!
                        }
                    }
                }
                div(classes = "e-content") {
                    unsafe {
                        +content(articleResource)
                    }
                }
                hr()
                br()
                br()
            }
        }

    }

    add(with(PageContext(
        "index.html",
        pageOgType = "website",
        blogIndexTitlesAndDescriptions,
        listOf(MermaidPlugin.activePluginName+"x")
    )) {
        asHtmlPage("h-feed", mergedArticles)
    })

    articles.forEach { articleResource ->
        val blogId = articleResource.blogId
        val titlesAndDescriptions = articleResource.titlesAndDescriptions
        val articlePage = with(PageContext(
            "blog/$blogId",
            pageOgType = "article",
            titlesAndDescriptions,
            listOf(MermaidPlugin.activePluginName+"x")// TODO md File -> article resource -> list
        )
        ) {
            asHtmlPage("h-entry", contentWithPermalink(articleResource))
        }
        add(articlePage)
    }
}
