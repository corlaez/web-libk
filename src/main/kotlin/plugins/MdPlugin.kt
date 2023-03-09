package plugins

import common.*
import common.OutputContext
import kotlinx.html.*
import notes.loadArticles
import templates.asHtmlPage
import utils.createDirectory

public open class MdPlugin(
    private val name: String,
    private val pathNamespace: String,
    private val localizedName: (Language) -> String,
    private val notesIndexTitlesAndDescriptions: (Language) -> TitlesAndDescriptions,
    private val navRel: String? = "",
    override val enabled: Boolean = true
) : WebPlugin {
    context(EnvContext, OutputContext)
    override fun pages(): List<Page> {
        return buildList {
            for (language in languages) {
                if (pathNamespace.isNotBlank()) createDirectory("deploy/output${language.langPath}$pathNamespace")
                with(LanguageContext(language)) {
                    with(PageContext(
                        "$pathNamespace$name.html",
                        pageOgType = "website",
                        notesIndexTitlesAndDescriptions(language))) {
                        val resources = loadArticles("/${language}/$name")// No pathNamespace here
                        val resource = resources.first()
                        add(asHtmlPage("", mdToHtml(resource.unparsedContent)))
                    }
                }
            }
        }
    }

    context(EnvContext, LanguageContext, PageContext, NAV)
    override fun navTags() {
        if(navRel != null) {
            this@NAV.a {
                if (pageUrl.contains("$pathNamespace$name.html")) {
                    classes = classes + "selected u-url"
                }
                href = "${language.langPath}$pathNamespace$name.html"
                rel = navRel
                +localizedName(language)
            }
            +" "
        }
    }
}
