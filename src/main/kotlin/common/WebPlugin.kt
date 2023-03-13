package common

import com.vladsch.flexmark.util.misc.Extension
import kotlinx.html.*

public interface WebPlugin {
    public val enabled: Boolean

    public fun flexmarkExtensions(): Collection<Extension> = emptyList()

    context(EnvContext, OutputContext)
    public fun pages(): List<Page> = emptyList()

    context(EnvContext, OutputContext, LanguageContext, PageContext, HEAD)
    public fun headTags() {}

    context(EnvContext, OutputContext, LanguageContext, PageContext, FOOTER)
    public fun footerTags() {}

    context(EnvContext, LanguageContext, PageContext, NAV)
    public fun navTags() {}

    context(EnvContext, LanguageContext, PageContext, HEADER)
    public fun headerTags() {}
}
