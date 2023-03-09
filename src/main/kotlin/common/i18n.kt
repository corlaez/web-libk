package common

import java.util.*

public data class Language(
    val langPath: String,
    val languageWithTerritory: String,
    val locale: Locale,
)

public val spanishPeruLanguage: Language = Language("/es/", "es_PE", Locale("es"))
public val englishUnitedStatesLanguage: Language = Language("/", "en_US", Locale.ENGLISH)

internal fun throwUnsupportedLanguage(): Nothing {
    error("Unsupported Language")
}

public class LocalizedText(language: Language) {
    public val legal: String = when(language) {
        englishUnitedStatesLanguage -> "Legal"
        spanishPeruLanguage -> "Legal"
        else -> throwUnsupportedLanguage()
    }
    public val notes: String = when(language) {
        englishUnitedStatesLanguage -> "Notes"
        spanishPeruLanguage -> "Apuntes"
        else -> throwUnsupportedLanguage()
    }
    public val lastUpdate: String = when(language) {
        englishUnitedStatesLanguage -> "Last update"
        spanishPeruLanguage -> "Última actualización"
        else -> throwUnsupportedLanguage()
    }
    public val notesIndexTitlesAndDescriptions: TitlesAndDescriptions = when(language) {
        englishUnitedStatesLanguage -> TitlesAndDescriptions(
            null,
            "",
            "Notes made by Armando Cordova",
            "Notes made by Armando Cordova. You will find notes about topics " +
                    "such as Violin, Kotlin and IndieWeb",
        )
        spanishPeruLanguage -> TitlesAndDescriptions(
            null,
            "",
            "Apuntes hechos por Armando Cordova",
            "Bienvenido al sitio web de Armando Cordova. " +
                    "Aquí encontrarás información sobre Violín, Kotlin y La Indie Web",
        )
        else -> throwUnsupportedLanguage()
    }
    public val logoAlly: String = when(language) {
        englishUnitedStatesLanguage -> "Logo that reads A R"
        spanishPeruLanguage -> "Logo con el texto A R"
        else -> throwUnsupportedLanguage()
    }
    public val thanksForYourVisit: String = when(language) {
        englishUnitedStatesLanguage -> "Thanks for your visit."
        spanishPeruLanguage -> "Gracias por la visita"
        else -> throwUnsupportedLanguage()
    }
    public val author: String = "Armando Cordova"
    public val messageBetweenWebsiteAndName: String = when(language) {
        englishUnitedStatesLanguage -> " has been brought to you by "
        spanishPeruLanguage -> " llega a usted gracias a "
        else -> throwUnsupportedLanguage()
    }
}

public class TitlesAndDescriptions(
    public val visibleTitle: String?,
    public val visibleDescription: String,
    public val metaTitle: String,
    public val metaDescription: String,
)
