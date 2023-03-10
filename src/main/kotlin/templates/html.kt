package templates

import common.*
import common.LanguageContext
import common.OutputContext
import common.PageContext
import kotlinx.html.*

context(EnvContext, OutputContext, LanguageContext, PageContext)
public fun asHtmlPage(mainClasses: String, contentHtml: String): Page {
    val htmlPageString = buildString {
        append("<!DOCTYPE html>")
        h().html {
            lang = language.locale.toString()
            head { headTags() }
            body(classes = mainClasses) {
                nav { navTags() }
                if (titlesAndDescriptions.visibleTitle != null) header { headerTags() }
                main(classes = "content${if(!isIndex) " e-content" else ""}") {
                    unsafe {
                        +contentHtml
                    }
                }
                footer {
                    webPlugins.forEach{ it.footerTags() }
                }
            }
        }
    }
    return Page(fileName, language.langPath, htmlPageString)
}

context(EnvContext, LanguageContext, PageContext)
private fun NAV.navTags() {
    webPlugins.forEach { it.navTags() }
}

context(EnvContext, OutputContext, LanguageContext, PageContext)
private fun HEADER.headerTags() {
    div(classes = "header-banner") {
        a(classes = "u-url") {
            href = pageUrl
            h1(classes = "p-name") {
                +titlesAndDescriptions.visibleTitle!!
            }
        }
        if (titlesAndDescriptions.visibleDescription.isNotBlank())
            p { +titlesAndDescriptions.visibleDescription }
    }
    webPlugins.forEach { it.headerTags() }
}

context(EnvContext, OutputContext, LanguageContext, PageContext)
private fun HEAD.headTags() {
    meta { charset = "utf-8" }
    link { rel = "canonical"; href = pageUrl }
    if (language != englishUnitedStatesLanguage) link {
        rel = "alternate"; hrefLang = englishUnitedStatesLanguage.locale.toString()
        href = domain + "${englishUnitedStatesLanguage.langPath}${path}"
    }
    if (language != spanishPeruLanguage) link {
        rel = "alternate"; hrefLang = spanishPeruLanguage.locale.toString()
        href = domain + "${spanishPeruLanguage.langPath}${path}"
    }
    link { rel = "stylesheet"; href = "/styles.css" }
    meta { name = "viewport"; content = "user-scalable=yes, width=device-width,initial-scale=1,shrink-to-fit=no" }
    meta { name = "robots"; content = "index, follow" }

    title { +titlesAndDescriptions.metaTitle }
    meta { name = "description"; content = titlesAndDescriptions.metaDescription }
    if (envText.LOGO_SQR_THEME_RGB != null)
        meta { name = "theme-color"; content = envText.LOGO_SQR_THEME_RGB }// ios Safari (modern)
    meta { name = "author"; content = envText.author(language) }

    meta { attributes += "property" to "og:title"; content=titlesAndDescriptions.metaTitle }
    meta { attributes += "property" to "og:description"; content=titlesAndDescriptions.metaDescription }
    meta { attributes += "property" to "og:url"; content=domain }
    meta { attributes += "property" to "og:type"; content=pageOgType }
    if (pageOgType == "article") {
//        meta { attributes += "property" to "og:article:published_time"; content="" }
//        meta { attributes += "property" to "og:article:modified_time"; content="" }
//        meta { attributes += "property" to "og:article:expiration_time"; content="" }
        meta { attributes += "property" to "og:article:author"; content=envText.author(language) }
//        meta { attributes += "property" to "og:article:section"; content="" }
//        meta { attributes += "property" to "og:article:tag"; content="" }
    }
    meta { attributes += "property" to "og:locale"; content=language.languageWithTerritory }
    meta { attributes += "property" to "og:site_name"; content=envText.WEBSITE_NAME }

    if (envText.LOGO_SQR_IMAGE_PATH != null) {
        meta { attributes += "property" to "og:image"; content=envText.LOGO_SQR_IMAGE_PATH }
        meta { name="twitter:image"; content=envText.LOGO_SQR_IMAGE_PATH }
    }
    meta { name="twitter:card"; content="summary_large_image" }
    envText.logoAlly(language)?.let {
        meta { name="twitter:image:alt"; content=it}
    }
    if (envText.TWITTER_HANDLE != null) {
        meta { name="twitter:creator"; content=envText.TWITTER_HANDLE }
        meta { name="twitter:site"; content=envText.TWITTER_HANDLE }
        meta { name="twitter:site_name"; content=envText.TWITTER_HANDLE }
    }

    meta { name="twitter:title"; content=titlesAndDescriptions.metaTitle  }
    meta { name="twitter:description"; content=titlesAndDescriptions.metaDescription }

    unsafe {
        +resources.faviconTags
    }
    if (envText.SERVICE_WORKER_JS_PATH != null)
        script {
            unsafe { +"""if('serviceWorker' in navigator){
            |navigator.serviceWorker.register("${envText.SERVICE_WORKER_JS_PATH}")}""".trimMargin() }
        }
    webPlugins.forEach{ it.headTags() }
}
