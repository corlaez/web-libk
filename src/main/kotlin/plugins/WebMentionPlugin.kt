package plugins

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*

public class WebMentionPlugin(override val enabled: Boolean = true) : WebPlugin {

    context(EnvContext, OutputContext, LanguageContext, PageContext, HEAD)
    override fun headTags() {
        this@HEAD.link { rel="webmention"; href="https://webmention.io/corlaez.com/webmention" }
        this@HEAD.link { rel="pingback"; href="https://webmention.io/corlaez.com/xmlrpc" }
    }
}
