package plugins

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*
import utils.loadResourceAsString
import utils.minifyCss

public enum class DevCss {
    NONE, BORDER, BORDER_AND_BACKGROUND
}

public class DevPlugin(private val devCss: DevCss = DevCss.NONE, override val enabled: Boolean = true) : WebPlugin {

    context(EnvContext, OutputContext, LanguageContext, PageContext, HEAD)
    override fun headTags() {
        if (arg.isDev()){// Check here because the EnvContext may be borrowed from the server
            this@HEAD.script {
                async = true
                defer = true
                unsafe {
                    +loadResourceAsString("/wsReload.js").replace("PORT", port)
                }
            }
            if (devCss == DevCss.BORDER || devCss == DevCss.BORDER_AND_BACKGROUND) {
                var css = loadResourceAsString("/css/dev.css")
                if (devCss == DevCss.BORDER) {
                    css = css.lines().filter { !it.contains("background") }.joinToString("").minifyCss()
                }
                this@HEAD.style {
                    unsafe {
                        +css
                    }
                }
            }
        }
    }
}
