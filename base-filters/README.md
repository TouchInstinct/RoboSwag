# Описание

Модуль содержит реализацию следующих типов фильтров:

1. Выбор одного из доступных значений списка
2. Выбор нескольких доступных значений из списка
3. <em>добавить остальные по ходу реализаации</em>

# Использование

## Выбор одного/нескольких из доступных значений списка

### Как использовать
``` kotlin
val selectorView = ListSelectionView(context)
                .setItems(navArgs.items)
                .addItemDecoration((TopDividerItemDecoration(
                        context = requireContext(),
                        drawableId = R.drawable.list_divider_1dp,
                        startMargin = START_MARGIN_DIVIDER_DP.px
                )))
                .withSelectionType(ListSelectionView.SelectionType.SINGLE_SELECT)
                .onResultListener { items ->
                    viewModel.dispatchAction(SelectItemAction.SelectItem(items)) }
                .build()
```
### Конфигурации
* в метод `setItems(List<RowSelectionItem>)` необходимо передать список объектов
* метод `addItemDecoration()` можно использовать для передачи объекта `RecyclerView.ItemDecoration`
* метод `withSelectionType()` используется для указания типа выбора:
  * `SINGLE_SELECT` - <em>по умолчанию</em> - позволяет выбрать один выариант, при этом будет выбран всегда как минимум один вариант
  * `MULTI_SELECT` - позволяет выбрать несколько вариантов из списка, при этом можно полностью выбрать все варианты и убрать выделение со всех вариантов
* колбэк `onResultListener()` можно использовать для получения списка всех элементов `RowSelectionItem` после каждого выбора
* колбэк `onItemClickListener()` можно использовать для получения элемента списка `RowSelectionItem`, по которому произошел клик
* после вызова конфигурационных методов обязательно необходимо вызать метод `build()`

### Кастомизация стиля

#### 1. Определить кастомную тему и стили элементов
1. Стиль для **текста элемента списка** должен быть наследником стиля `Widget.FilterSelection.Item`
``` xml
<style name="Widget.Custom.FilterSelection.Item" parent="@style/Widget.FilterSelection.Item">
        <item name="android:textAppearance">@style/Text15sp.Regular.Black</item>
        <item name="android:paddingTop">2dp</item>
        <item name="android:lineSpacingExtra">3sp</item>
        <item name="android:translationY">-1.71sp</item>
</style>
```
2. Стиль для **индикатора выбора** должен быть наследником стиля `Widget.FilterSelection.Radio`
Передайте `selector-drawable` для кастомизации вида индикатора в конце строки
``` xml
<style name="Widget.Custom.FilterSelection.Radio" parent="@style/Widget.FilterSelection.Radio">
        <item name="android:button">@drawable/selector_checkbox</item>
</style>
```
3. Создайте **тему**, которая должна быть наследником `Theme.FilterSelection`
``` xml
<style name="Theme.Custom.FilterSelection" parent="@style/Theme.FilterSelection">
        <item name="sheetSelection_itemStyle">@style/Widget.Custom.FilterSelection.Item</item>
        <item name="sheetSelection_radioStyle">@style/Widget.Custom.FilterSelection.Radio</item>
</style>
```
#### 2. Применить тему при создании view
При создании вью в коде можно указать тему, используя `ContextThemeWrapper`
``` kotlin
val newContext = ContextThemeWrapper(requireContext(), R.style.Theme_Custom_FilterSelection)
val selectorView = ListSelectionView(newContext)
                  ...
                  .build()
```
