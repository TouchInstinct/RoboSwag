package ru.touchin.roboswag.core.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Util object for handling some cases with DateTime e.g. parsing string to DateTime object
 */
object DateFormatUtils {

    enum class Format(val formatValue: String) {
        DATE_TIME_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSSZZ"),
        DATE_FORMAT("yyyy-MM-dd"),
        TIME_FORMAT("HH:mm:ssZ")
    }

    /**
     * @return the result of parsed string value
     * @param value is string value of date time in right format
     * @param format is date time format for parsing string value.
     * Default value is [Format.DATE_TIME_FORMAT]
     * @param defaultValue is value returned in case of exception
     */
    fun fromString(
            value: String,
            format: Format = Format.DATE_TIME_FORMAT,
            defaultValue: DateTime? = null
    ): DateTime? = runCatching { value.parse(format.formatValue) }.getOrDefault(defaultValue)

    private fun String.parse(format: String) = DateTimeFormat.forPattern(format).parseDateTime(this)

}
