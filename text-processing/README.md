text-processing
=====

### Общее описание

Модуль содержит функционал:
- Генерация "Replace" шаблона из регулярного выражения и возможность подстановки его в строку,
- Генерация Placeholder из регулярного выражения,
- Генерация маски для `EditText` из регулярного выражения и возможность его подстановки.

Модуль содержит класс `TextFormatter`, который в качестве аргумента принимает `String` в виде регулярного выражения.
* Функция `getFormattedText` - принимает входящий параметр в виде строки для форматирования, возвращает отформатированную строку,
* Функция `getPlaceholder` - возвращает Placeholder соответствующий регулярному выражению,
* Функция `getRegexReplace` - возвращает "Replace" шаблон регулярного выражения,
* Функция `mask` - принимает входящим параметром `EditText` и применяет к нему маску сгенерированую по регулярному выражению.

### Пример применения `textFormatter`

```kotlin
class MainActivity : Activity() {

    /** 
     * replace шаблон - $1/$2
     * placeholder - 12/34
     * **/
    private val textFormatter = TextFormatter("(\\d{2})\\/?(\\d{2})")
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        editText.hint = textFormatter.getPlaceholder() //В результате placeholder будет 12/34
        textFormatter.mask(editText) //Применение маски соответствующей регулярному выражению из textFormatter
    }
}
```