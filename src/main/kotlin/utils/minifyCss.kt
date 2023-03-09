package utils

import common.EnvContext

context(EnvContext)
public fun String.minifyCss(): String = if(prettyPrint) this else this
    .replace(Regex("\\{\\s+"), "{")
    .replace(Regex(":\\s+"), ":")
    .replace(Regex(",\\s+"), ",")
    .replace(Regex(";\\s+"), ";")
    .replace(Regex("}\\s+"), "}")
    .replace(Regex("/\\*\\s+"), "/*")
    .replace(Regex("\\*/\\s+"), "*/")
