package notes

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*
import templates.asHtmlPage
import utils.listFilenamesInDirectory
import utils.loadResourceAsString

internal fun loadArticles(folder: String): List<NoteResource> {
    return listFilenamesInDirectory(folder)
        .map { NoteResource(it, loadResourceAsString("$folder/$it")) }
        .sortedByDescending { it.id }
}

context(EnvContext, LanguageContext, OutputContext)
internal fun MutableList<Page>.addBlogPages(
    articles: List<NoteResource>,
    notesIndexTitlesAndDescriptions: TitlesAndDescriptions
) {
    // todo p-category

    val contents = articles.map { articleResource ->
        val outputFileName = articleResource.outputFileName
        val titlesAndDescriptions = articleResource.titlesAndDescriptions
        with(PageContext("note/$outputFileName", pageOgType = "article", titlesAndDescriptions)) {
            val c = contentWithPermalink(articleResource)
            add(asHtmlPage("h-entry", c))
            c
        }
    }

    val mergedArticles = contents.joinToString("") { content ->
        createH().div(classes = "h-entry") {
            div(classes = "e-content") {
                unsafe {
                    +content
                }
            }
            hr()
            br()
            br()
        }
    }

    add(with(PageContext("note/index.html", pageOgType = "website", notesIndexTitlesAndDescriptions)) {
        asHtmlPage("h-feed", mergedArticles)
    })
}
