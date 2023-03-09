package md

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet
import common.WebPlugin

internal class MarkdownSupport(private val webPlugins: List<WebPlugin>) {
    private val parserOptions = with(MutableDataSet()) {
//        uncomment to set optional extensions
//        set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));
//        uncomment to convert soft-breaks to hard breaks
//        set(HtmlRenderer.SOFT_BREAK, "<br />\n")
        set(Parser.EXTENSIONS, webPlugins.flatMap { it.flexmarkExtensions() });
        this
    }
    private val htmlRendererOptions = MutableDataSet()
    private val parser: Parser = Parser.builder(parserOptions).build()
    private val htmlRenderer = HtmlRenderer.builder(htmlRendererOptions).build()

    internal fun mdToHtml(inputMarkdown: String) = htmlRenderer.render(parser.parse(inputMarkdown)).replace(">\n", ">")
}
