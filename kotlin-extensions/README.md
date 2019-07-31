kotlin-extensions
=====

Модуль содержит extension-функции для `Activity`, `Context`, `Delegates`, `TextView`, `View` и `ViewHolder`.

### Основные интерфейсы и классы

##### Расширения для `Activity`:

* *safeStartActivityForResult* - функция для запуска нового активити с `requestCode`, который будет передан в *onActivityResult* при завершении работы данного активити. Находит наиболее подходящий активити для выполнения действия. Если не будет найден ни один активити для выполнения действия, то функция ничего не сделает и вернет `false`.

##### Расширения для `Context`:

* *safeStartActivity* - функция запуска активити, аналогична *safeStartActivityForResult*, но не позволяет передать `requestCode`.

* *openBrowser* - функция для открытия ссылки в браузере через *safeStartActivity*.

* *callToPhoneNumber* - функция для открытия программы "Телефон" с переданным номером телефона через *safeStartActivity*.

##### Расширения для `TextView`:

* *drawableStart*, *drawableTop*, *drawableEnd*, *drawableBottom* - функции для установки и получения `Drawable` на соответсвующих позициях.

##### Расширения для `View`:

* *setOnRippleClickListener* - функция для добавления Ripple-эффекта и действия, которое будет выполняться при нажатии на `View`.

##### Расширения для `ViewHolder`:

* *ViewHolder.findViewById* - функция для поиска `View`, расположеного внутри *itemView*.

* *ViewHolder.getText* - функция для получения текста из ресурсов.

* *ViewHolder.getString* - функция для получения строк из ресурсов. Может также принимать вторым аргументом и далее - строки, которые будут подставлены вместо специальных символов в строку из ресурсов.

* *ViewHolder.getColor* - получить цвет в виде `Int` из ресурсов.

* *ViewHolder.getColorStateList* - получить `ColorStateList`, который ассоциируется с переданным цветом.

* *ViewHolder.getDrawable* - получить `Drawable` из ресурсов.

##### Расширения для `Delegates`:

* *observable* - подписка на изменения свойства, принимает `initialValue` - начальное значение и `onChange` - действие, которое будет выполняться после каждой установки свойства.

* *distinctUntilChanged* - тоже самое, что и предыдущее расширение, только `onChange` будет выполняться лишь в том случае, когда свойство принимает новое значение не равное `null` и отличное от предыдущего.

### Примеры

```kotlin
class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val linkView: TextView = findViewById(R.id.item_link)
    private val linkColor = getColor(R.color.global_action)

    fun bind(link: String) {
        linkView.text = link
        linkView.setOnRippleClickListener { context.openBrowser(link) }
    }
}
```

### Подключение

```gradle
implementation project(':kotlin-extensions')
```
