recyclerview-calendar
====

Календарь с гибкими настройками внешнего вида заголовка и ячеек. Позволяет выделять и работать с заданным промежутком времени. 

### Основные интерфейсы и классы

Класс `CalendarRecyclerView` - класс, который наследуется от `RecyclerView` и позволяет работать с `CalendarAdapter`.

Абстрактный класс `CalendarAdapter` наследуется от `RecyclerView.Adapter`. Имеет три параметра: 
* `TDayViewHolder` - вьюхолдер с датой, 
* `THeaderViewHolder` - вьюхолдер с заголовком - названием месяца, 
* `TEmptyViewHolder` - вьюхолдер для пустых ячеек.

В конструкторе принимает *startDate* - дата начала календаря, *endTime* - дата конца календаря и *vararg* названий месяцев. Первые два параметра имеют тип `DateTime`.

Методы:

* *updateCalendarItems* - обновить календарь, принимает два `DateTime`: начало и конец календаря.

* *setSelectedRange* - отметить непрерывный диапазон дат, принимает начало и конец диапазона в виде объектов `DateTime`.

* *getPositionToScroll* - получить позицию к которой происходит скролл при открытии календаря.

* *getCalendarItems* - полуить список ячеек(`CalendarItem`) календаря.

* *getMonthsNameByHeaderCalendarItem* - получить название месяца для переданного `CalendarHeaderItem`.

При релизации данного класса, необходимо реализовать следующие методы:

* *createHeaderViewHolder* - создание `ViewHolder` для заголовка месяца.

* *createEmptyViewHolder* - создание `ViewHolder` для пустой ячейки.

* *createDayViewHolder* - создание `ViewHolder` для ячейки с числом.

* *bindHeaderItem* - прикрепление информации о месяце и годе к строке-названию месяца.

* *bindEmptyItem* - прикрепление информации о состоянии выбора к пустой ячейке.

* *bindDayItem* - прикрепление информации о дне ко `ViewHolder`.


У класса `CalendarAdapter` есть вложенный класс-перечисление `SelectionMode` для отметки ячейки при выборе диапазона. У данного перечисления может быть 5 значений: 
* `SELECTED_FIRST`,
* `SELECTED_MIDDLE`, 
* `SELECTED_LAST`, 
* `SELECTED_ONE_ONLY`, 
* `NOT_SELECTED`

Интерфейс `CalendarItem` описывает элемент календаря. Имеет два метода *getStartRange* - получить начало календаря и *getEndRange* - получить конец календаря.

Реализации данного интерфейса: `CalendarDayItem`, `CalendarEmptyItem` и `CalendarHeaderItem`.

Класс `CalendarDayItem` - описывает один сплошной диапазон с датами. У него есть поле *positionOfFirstDate* - это число, с которого начинается данный диапазон, *dateOfFirstDay* - дата первого дня диапазона в миллисекундах и *comparingToToday* - отношение диапазона к сегодняшнему дню. Для "сегодня" используется отдельный элемент *startRange* и *endRange* которого равны.

Класс `CalendarEmptyItem` - описывает диапазон пустых ячеек.

Класс `CalendarHeaderItem` - описывает заголовок месяца. Имеет информацию о годе и месяце.

Класс `CalendarUtils` содержит методы для работы с `CalendarItem`:

* *findItemByPosition* - найти элемент по его позиции. Принимает список `CalendarItem` и позицию.

* *findPositionOfSelectedMonth* - найти позицию элемента-заголовка месяца, в которому относится переданная позиция. Принимает список `CalendarItem` и позицию.

* *findPositionByDate* - найти позицицию переданной даты. Принимает список `CalendarItem` и дату в миллисекундах.

* *fillRange* - заполнить заданный диапазон и получить список `CalendarItem`. Принимает *startDate* и *endDate* в виде объектов `DateTime`.

### Примеры

В примере используется `ViewController` из модуля [navigation](https://github.com/TouchInstinct/RoboSwag/tree/master/navigation)

Файл `CalendarDataImpl.kt` - реализация `CalendarAdapter`

```Kotlin
class CalendarAdapterImpl(
        startDay: DateTime,
        endDate: DateTime
) : CalendarAdapter<CalendarDayViewHolder, CalendarHeaderViewHolder,
        CalendarEmptyViewHolder>(startDay, endDate, *months) {

    companion object {
        val months = arrayOf("Январь", "Февраль" /* and others */)
    }

    private var onDateClickedListener: ((DateTime) -> Unit)? = null

    override fun createHeaderViewHolder(parent: ViewGroup): CalendarHeaderViewHolder =
            CalendarHeaderViewHolder(UiUtils.inflate(R.layout.item_calendar_header_view, parent))

    override fun createEmptyViewHolder(parent: ViewGroup): CalendarEmptyViewHolder =
            CalendarEmptyViewHolder(UiUtils.inflate(R.layout.item_calendar_empty_view, parent))

    override fun createDayViewHolder(parent: ViewGroup): CalendarDayViewHolder =
            CalendarDayViewHolder(UiUtils.inflate(R.layout.item_calendar_day_view, parent))

    override fun bindHeaderItem(
            viewHolder: CalendarHeaderViewHolder,
            year: Int,
            monthName: String,
            firstMonth: Boolean
    ) {
        viewHolder.bind("$monthName $year")
    }

    override fun bindEmptyItem(viewHolder: CalendarEmptyViewHolder, selectionMode: SelectionMode) {
        viewHolder.bind(selectionMode)
    }

    override fun bindDayItem(
            viewHolder: CalendarDayViewHolder,
            day: String,
            date: DateTime,
            selectionMode: SelectionMode,
            dateState: ComparingToToday
    ) {
        viewHolder.bind(day, date, selectionMode, dateState, onDateClickedListener)
    }

    fun setOnDateClickedListener(onDateClickedListener: (DateTime) -> Unit) {
        this.onDateClickedListener = onDateClickedListener
        notifyDataSetChanged()
    }

}

```

Файл `PeriodsViewController.kt`

```Kotlin
class PeriodsViewController(
        creationContext: CreationContext
) : ViewController<MainActivity, PeriodState>(
        creationContext,
        R.layout.view_controller_period
) {

    private val calendarRecyclerView: CalendarRecyclerView = 
        findViewById(R.id.view_controller_period_calendar)

    init {
        val startPeriod = DateTime().withYear(2018).withDayOfYear(1)
        val endPeriod = DateTime.now().plusMonths(1).dayOfMonth().withMaximumValue()

        val calendarAdapter = CalendarAdapterImpl(startPeriod, endPeriod)

        calendarAdapter.setOnDateClickedListener { dateTime ->

            // Do something

        }
        calendarRecyclerView.setAdapter(calendarAdapter)
    }

}
```

Файл `view_controller_period.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="16dp"
        android:text="Календарь"/>

    <ru.touchin.calendar.CalendarRecyclerView
        android:id="@+id/view_controller_period_calendar"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white"
        android:overScrollMode="never"/>

</LinearLayout>
```
### Зависимости

Для работы с данным модулем необходимо так же подключить модуль [logging](https://github.com/TouchInstinct/RoboSwag/tree/master/logging).

```gradle
implementation project(':logging')
```
