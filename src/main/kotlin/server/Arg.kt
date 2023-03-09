package server

public enum class Arg {
    // Spin local server and adds hot reloading scripts, assumes local domain
    dev,
    // Spin local server and adds hot reloading scripts, assumes prod domain
    prd,
    // Expects a running dev or prd server. Refreshes the changes
    regenerate,
    // Assumes prod domain, but doesn't run local server or hot reloading scripts
    release;

    internal fun isDev() = this == dev
    internal fun isPrd() = this == prd || this == release // Release is considered prd for most purposes
    internal fun isRegenerate() = this == regenerate
    internal fun isRelease() = this == release // Used when release must behave different from prd
}
