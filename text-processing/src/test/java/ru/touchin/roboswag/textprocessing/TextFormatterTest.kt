package ru.touchin.roboswag.textprocessing

import org.junit.Assert
import org.junit.Test

class TextFormatterTest {

    @Test
    fun test_bank_card_expire_formatting() {
        val regex = "(\\d{2})\\/?(\\d{2})"
        val inputText = "0622"
        val item = TextFormatter(regex)
        Assert.assertEquals("$1\\/$2", item.getRegexReplace())
        Assert.assertEquals("06/22", item.getFormatText(inputText))
    }

    @Test
    fun test_number_bank_card_formatting() {
        val regex = "(\\d{4}) ?(\\d{4}) ?(\\d{4}) ?(\\d{4})"
        val inputText = "1234345612353534"
        val item = TextFormatter(regex)
        Assert.assertEquals("\$1 \$2 \$3 \$4", item.getRegexReplace())
        Assert.assertEquals("1234 3456 1235 3534", item.getFormatText(inputText))
    }

    @Test
    fun test_phone_number_formatting() {
        val regex = "(?:\\+7 )?\\(?(\\d{3})\\)? ?(\\d{3}) ?(\\d{2}) ?(\\d{2})"
        val inputText = "9091344422"
        val item = TextFormatter(regex)
        Assert.assertEquals("\\+7 \\($1\\) $2 $3 $4", item.getRegexReplace())
        Assert.assertEquals("+7 (909) 134 44 22", item.getFormatText(inputText))
    }

    @Test
    fun test_birthday_certificate_number_formatting() {
        val regex = "([A-Z]{2})-?([А-Я]{2}) ?№? ?(\\d{6})"
        val inputText = "IVБЮ349823"
        val item = TextFormatter(regex)
        Assert.assertEquals("\$1-\$2 № \$3", item.getRegexReplace())
        Assert.assertEquals("IV-БЮ № 349823", item.getFormatText(inputText))
    }

    @Test
    fun test_rub_sum_formatting() {
        val regex = "(\\d+)([.,]\\d+)? ?₽?"
        val inputText = "5332.4"
        val item = TextFormatter(regex)
        Assert.assertEquals("\$1\$2 ₽", item.getRegexReplace())
        Assert.assertEquals("5332.4 ₽", item.getFormatText(inputText))
    }
}