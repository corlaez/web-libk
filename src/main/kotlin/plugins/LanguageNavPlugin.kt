package plugins

import common.*
import common.LanguageContext
import kotlinx.html.*

public class LanguageNavPlugin(
    private val languageToLinkText: (Language) -> String,
    override val enabled: Boolean = true
) : WebPlugin {

    context(EnvContext, LanguageContext, PageContext, NAV)
    override fun navTags() {
        languages.forEach {
            if (it != language) {
                +" "
                this@NAV.a { href = "${it.langPath}${path}"; +languageToLinkText(it) }
            }
        }
    }
}
