import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.tz.UTCProvider
import org.junit.Assert
import org.junit.Test
import ru.touchin.roboswag.core.utils.DateFormatUtils

class DateFormatUtilsTest {

    init {
        DateTimeZone.setProvider(UTCProvider())
    }

    @Test
    fun `Assert Date format parsing`() {
        val dateTime = DateFormatUtils.fromString(
                value = "2015-04-29",
                format = DateFormatUtils.Format.DATE_FORMAT
        )
        Assert.assertEquals(DateTime(2015, 4, 29, 0, 0, 0), dateTime)
    }

    @Test
    fun `Assert Date format parsing with default value`() {
        val currentDateTime = DateTime.now()

        val dateTime = DateFormatUtils.fromString(
                value = "2015-04-29",
                format = DateFormatUtils.Format.DATE_TIME_FORMAT,
                defaultValue = currentDateTime
        )
        Assert.assertEquals(currentDateTime, dateTime)
    }
}
