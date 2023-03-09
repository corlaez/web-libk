package plugins

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*

public class IndieWebRingPlugin(override val enabled: Boolean = true) : WebPlugin {

    context(EnvContext, OutputContext, LanguageContext, PageContext, FOOTER)
    override fun footerTags() {
        this@FOOTER.p(classes = "center") {
            a(classes = "u-url") {
                href = "https://xn--sr8hvo.ws/%F0%9F%94%AE%F0%9F%90%9C%F0%9F%9A%87/previous"
                rel = envText.EXTERNAL_RELS
                +"←"
            }
            +" An "
            a(classes = "u-url") {
                href = "https://xn--sr8hvo.ws"
                rel = envText.EXTERNAL_RELS
                +"IndieWeb Webring"
            }
            +" \uD83D\uDD78\uD83D\uDC8D "
            a(classes = "u-url")  {
                href = "https://xn--sr8hvo.ws/%F0%9F%94%AE%F0%9F%90%9C%F0%9F%9A%87/next"
                rel = envText.EXTERNAL_RELS
                +"→"
            }
        }
    }
}
