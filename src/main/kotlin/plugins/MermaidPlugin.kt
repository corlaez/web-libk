package plugins

import com.vladsch.flexmark.ext.gitlab.GitLabExtension
import com.vladsch.flexmark.util.misc.Extension
import common.*
import common.LanguageContext
import common.OutputContext
import kotlinx.html.*

public class MermaidPlugin(override val enabled: Boolean = true) : WebPlugin {

    internal companion object {
        const val activePluginName = "htmx"
    }

    // alters generation of mermaid code blocks to be a div with class mermaid as the js expects
    override fun flexmarkExtensions(): Collection<Extension> = listOf(GitLabExtension.create())

    context(EnvContext)
    override fun pages(): List<Page> {
        return emptyList()// TODO add mermaid js from webjar
    }

    // loads mermaid js
    context(EnvContext, OutputContext, LanguageContext, PageContext, FOOTER)
    override fun footerTags() {
        if(activePlugin.contains(activePluginName)) {
            this@FOOTER.script {
                unsafe { +(
                        "window.onload=function(){" +
                                "mermaid.initialize({" +
                                "'theme':'dark','background':'#111111'" +
                                "});" +
                                "};"
                        )
                }
            }
            this@FOOTER.script { defer = true; src ="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js" }
        }
    }
}
