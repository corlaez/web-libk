package common

public object ContantHelper {
    private val list = mutableListOf<Pair<String, String>>()

    public fun String.replaceTemplateConstants(): String = list.fold(this) { acc, pair ->
        acc.replace(pair.first, pair.second)
    }

    public fun register(pair: Pair<String, String>): String = pair.let {
        list.add(it)
        it.second
    }
}

//internal object C {
//    val DOMAIN = register("DOMAIN" to "https://corlaez.com")
//    val OWNER_NAME = register("OWNER_NAME" to "Armando Cordova")
//    val SIGNATURE_IMAGE_PATH = register("SIGNATURE_IMAGE_PATH" to "/assets/signature-400.webp")
//    val SIGNATURE2_IMAGE_PATH = register("SIGNATURE2_IMAGE_PATH" to "/assets/signature-white-210.png")
//    val SIGNATURE2_IMAGE_W = register("SIGNATURE2_IMAGE_W" to "210")
//    val SIGNATURE2_IMAGE_H = register("SIGNATURE2_IMAGE_H" to "210")
//    // referenced directly in the html template
//    val BEC_AUDIO = register("BEC_AUDIO" to "/assets/bec.mp3")
//    val BEC_AUDIO_TYPE = register("BEC_AUDIO_TYPE" to "audio/mpeg")
//    val WINE_IMAGE_PATH = register("WINE_IMAGE_PATH" to "/assets/dark-red.webp")// preload
//
//    val THEME_DIM_RGB = register("THEME_DIM_RGB" to "#fd7777")
//    val THEME_STD_RGB = register("THEME_STD_RGB" to "#ff4242")
//    val THEME_DARK_RGB = register("THEME_DARK_RGB" to "#bd1e1e")
//
//    val BACKGROUND_RGB = register("BACKGROUND_RGB" to "#131516")
//    val LINK_RGB = register("LINK_RGB" to THEME_STD_RGB)
//    val LINK_VISITED_RGB = register("LINK_VISITED_RGB" to THEME_DIM_RGB)
//    val AUDIO_RGB = register("AUDIO_RGB" to THEME_DARK_RGB)
//    val TWITTER_HANDLE = register("TWITTER_HANDLE" to "@corlaez")
//    val WEBSITE_NAME = register("WEBSITE_NAME" to "Corlaez Blog")
//    val LOGO_SQR_IMAGE_PATH = register("LOGO_SQR_IMAGE_PATH" to "/assets/banner2.png")
//    val LOGO_SQR_THEME_RGB = register("LOGO_SQR_THEME_RGB" to "#A10000")
//    val SERVICE_WORKER_JS_PATH = register("SERVICE_WORKER_JS_PATH" to "/serviceWorker.js")
//}
