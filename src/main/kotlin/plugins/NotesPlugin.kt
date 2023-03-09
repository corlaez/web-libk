package plugins

import common.*
import common.LanguageContext
import common.OutputContext
import notes.addBlogPages
import notes.loadArticles
import kotlinx.html.*
import utils.createDirectory

public class NotesPlugin(
    private val notesIndexTitlesAndDescriptions: (Language) -> TitlesAndDescriptions,
    override val enabled: Boolean = true
) : WebPlugin {
    context(EnvContext, OutputContext)
    override fun pages(): List<Page> {
        for (language in languages) {
            createDirectory("deploy/output${language.langPath}note")
        }
        val articles = languages.associateWith { loadArticles("/${it.locale}/notes") }

        return buildList {
            articles.forEach { (lang, articles) ->
                with(LanguageContext(lang)) {
                    addBlogPages(articles, notesIndexTitlesAndDescriptions(lang))
                }
            }
        }
    }

    context(EnvContext, LanguageContext, PageContext, NAV)
    override fun navTags() {
        this@NAV.a {
            if (path.contains("note")) {
                classes = classes + "selected"
                if (isIndex) classes = classes + "u-url"
            }
            href = "${language.langPath}note"
            +t.notes
        }
        +" "
    }
}
