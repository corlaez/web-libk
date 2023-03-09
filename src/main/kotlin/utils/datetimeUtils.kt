package utils

import common.LanguageContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

internal fun toDateTime(s: String) = LocalDateTime.of(LocalDate.parse(s), LocalTime.MIDNIGHT)
    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

context(LanguageContext)
internal fun toHumanDate(s: String) = LocalDate.parse(s)
    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(language.locale))
