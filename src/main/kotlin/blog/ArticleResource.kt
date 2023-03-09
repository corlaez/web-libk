package blog

import common.TitlesAndDescriptions

internal class ArticleResource(name: String, unparsedContent: String) {
    val id: Int = name.split("$")[0].toInt()
    val blogId: String
    val titlesAndDescriptions: TitlesAndDescriptions
    val createdDate: String
    val modifiedDate: String
    val rawContent: String

    init {
        val unparsedContentLines = unparsedContent.lines()
        blogId = unparsedContentLines[0]
        titlesAndDescriptions = TitlesAndDescriptions(
            unparsedContentLines[1],
            unparsedContentLines[2],
            unparsedContentLines[3],
            unparsedContentLines[4],
        )
        createdDate = unparsedContentLines[5]
        modifiedDate = unparsedContentLines[6]
        rawContent = unparsedContentLines.subList(7, unparsedContentLines.size).joinToString("\n")
    }
}
