import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.touchin.roboswag.core.utils.DateFormatUtils

class DateFormatUtilsTest {

    @Test
    fun `Assert Date format parsing`() {
        val dateTime = DateFormatUtils.fromString(
                value = "2015-04-29",
                format = DateFormatUtils.Format.DATE_FORMAT
        )
        assertEquals(DateTime(2015, 4, 29, 0, 0, 0), dateTime)
    }

    @Test
    fun `Assert Date format parsing with default value`() {
        val currentDateTime = DateTime.now()

        val dateTime = DateFormatUtils.fromString(
                value = "2015-04-29",
                format = DateFormatUtils.Format.DATE_TIME_FORMAT,
                defaultValue = currentDateTime
        )
        assertEquals(currentDateTime, dateTime)
    }
}
