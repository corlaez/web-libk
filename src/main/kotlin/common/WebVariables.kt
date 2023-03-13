package common

public object WebVariables {
    private val map = mutableMapOf<String, String>()

    public fun String.replaceWebVariables(): String = map.keys.fold(this) { acc, key ->
        acc.replace(key, map[key]!!)
    }

    public operator fun get(key: String): String = map[key]!!

    public fun getNullable(key: String): String? = map[key]

    public fun register(pair: Pair<String, String>): String = pair.let {
        map[it.first] = it.second
        it.second
    }
}
