package blog

import common.EnvContext
import common.LanguageContext
import common.PageContext
import kotlinx.html.*
import utils.toDateTime
import utils.toHumanDate

context(EnvContext, LanguageContext)
private fun ArticleResource.unsafeLastUpdate() = if (modifiedDate.isBlank()) "" else buildString {
    append(" (${t.lastUpdate}: ")
    h().time(classes = "dt-updated") {
        dateTime = toDateTime(modifiedDate)
        +toHumanDate(modifiedDate)
    }
    append(")")
}

context(EnvContext, LanguageContext)
private fun FlowOrPhrasingContent.datesHtml(articleResource: ArticleResource) {
    em {
        time(classes = "dt-published") {
            dateTime = toDateTime(articleResource.createdDate)
            +toHumanDate(articleResource.createdDate)
        }
        unsafe {
            +articleResource.unsafeLastUpdate()
        }
    }
}

context(EnvContext, LanguageContext)
internal fun content(articleResource: ArticleResource) =  buildString {
    h().p {
        datesHtml(articleResource)
    }
    append(mdToHtml(articleResource.rawContent))
}


context(EnvContext,LanguageContext, PageContext)
internal fun contentWithPermalink(articleResource: ArticleResource) = buildString {
    h().p {
        a(classes = "u-url") {
            href = pageUrl
            datesHtml(articleResource)
        }
    }
    append(mdToHtml(articleResource.rawContent))
}
