package plugins

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*

public class IndieAuthPlugin(override val enabled: Boolean = true) : WebPlugin {

    context(EnvContext, OutputContext, LanguageContext, PageContext, HEAD)
    override fun headTags() {
        // avoid using indieauth.com as it is shutting down. Spin up own server or find alternative
        this@HEAD.link { rel = "authorization_endpoint"; href = "https://indieauth.com/auth" }
        this@HEAD.link { rel = "token_endpoint"; href = "https://tokens.indieauth.com/token" }
    }
}
