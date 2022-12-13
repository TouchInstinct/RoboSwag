package ru.touchin.roboswag.textprocessing

import org.junit.Assert
import org.junit.Test

class TextProcessingBuilderTest {

    @Test
    fun test_task1() {
        val regex = "(\\d{2})\\/?(\\d{2})"
        val inputText = "0622"
        val item = TextProcessingBuilder(
            regex = regex,
            inputText = inputText
        )
        Assert.assertEquals("$1\\/$2",item.regexReplaceString)
        Assert.assertEquals("06/22",item.formatString)
//        Assert.assertEquals("12/34",item.placeholder)
    }

    @Test
    fun test_task2() {
        val regex = "(\\d{4}) ?(\\d{4}) ?(\\d{4}) ?(\\d{4})"
        val inputText = "1234345612353534"
        val item = TextProcessingBuilder(
            regex = regex,
            inputText = inputText
        )
        Assert.assertEquals("\$1 \$2 \$3 \$4",item.regexReplaceString)
        Assert.assertEquals("1234 3456 1235 3534",item.formatString)
//        Assert.assertEquals("1234 5678 9012 3456",item.placeholder)
    }

    @Test
    fun test_task3() {
        val regex = "(?:\\+7 )?\\(?(\\d{3})\\)? ?(\\d{3}) ?(\\d{2}) ?(\\d{2})"
        val inputText = "9091344422"
        val item = TextProcessingBuilder(
            regex = regex,
            inputText = inputText
        )
        Assert.assertEquals("\\+7 \\($1\\) $2 $3 $4",item.regexReplaceString)
        Assert.assertEquals("+7 (909) 134 44 22",item.formatString)
//        Assert.assertEquals("+7 (123) 456 78 90",item.placeholder)
    }

    @Test
    fun test_task4() {
        val regex = "([A-Z]{2})-?([А-Я]{2}) ?№? ?(\\d{6})"
        val inputText = "IVБЮ349823"
        val item = TextProcessingBuilder(
            regex = regex,
            inputText = inputText
        )
        Assert.assertEquals("\$1-\$2 № \$3",item.regexReplaceString)
        Assert.assertEquals("IV-БЮ № 349823",item.formatString)
//        Assert.assertEquals("AB-АБ № 123456",item.placeholder)
    }
}