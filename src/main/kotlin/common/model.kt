package common

internal data class Resources(
    val sytlesCss: String,
    val faviconTags: String,
    val manifestJson: String,// Google PWA
    val browserconfigXml: String,// Windows 8+
)

public data class Page(val name: String, val namespace: String, val content: String)
