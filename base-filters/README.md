# Описание

Модуль содержит реализацию следующих типов фильтров:

1. Выбор одного/нескольких из доступных значений списка
2. Выбор одного/нескольких значений из перечня тегов

# Использование

## 1. Выбор одного/нескольких из доступных значений списка

### Как использовать
``` kotlin
val selectorView = ListSelectionView<DefaultSelectionItem, SelectionItemViewHolder<DefaultSelectionItem>>(context)
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
* при создании `ListSelectionView<ItemType, HolderType>` необходимо передлать `ItemType` - класс модели данных в списке, `HolderType` - класс viewHolder-а в recyclerView.
Для использования дефолтной реализации необходимо использовать типы `<DefaultSelectionItem, SelectionItemViewHolder<DefaultSelectionItem>>`
* в метод `setItems(List<ItemType>)` необходимо передать список объектов
* метод `addItemDecoration(itemDecoration: RecyclerView.ItemDecoration)` можно использовать для передачи объекта `RecyclerView.ItemDecoration`
* метод `withSelectionType(type: SelectionType)` используется для указания типа выбора:
  * `SINGLE_SELECT` - <em>по умолчанию</em> - позволяет выбрать один выариант, при этом будет выбран всегда как минимум один вариант
  * `MULTI_SELECT` - позволяет выбрать несколько вариантов из списка, при этом можно полностью выбрать все варианты и убрать выделение со всех вариантов
* метод `showInHolder(HolderFactoryType<ItemType>)` используется для определения кастомного viewHolder для списка с недефолтной разметкой.
``` kotlin
val selectorView = ListSelectionView<TestSelectionItem, TestItemViewHolder>(context)
                .showInHolder { parent, clickListener, selectionType ->
                    TestItemViewHolder(
                            binding = TestSelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                            onItemSelectAction = clickListener,
                            selectionType = selectionType
                    )
                }
                ...
                .build()
```
* колбэк `onSelectedItemsListener(listener: OnSelectedItemsListener<ItemType>)` можно использовать для получения списка всех элементов `ItemType` после каждого выбора
* колбэк `onSelectedItemListener(listener: OnSelectedItemListener<ItemType>)` можно использовать для получения элемента списка `ItemType`, по которому произошел клик
* после вызова конфигурационных методов обязательно необходимо вызать метод `build()`

### Кастомизация стиля дефолтной реализации ViewHolder без необходимости создания кастомного layout и viewHolder

#### 1) Определить кастомную тему и стили элементов
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
#### 2) Применить тему при создании view
При создании вью в коде можно указать тему, используя `ContextThemeWrapper`
``` kotlin
val newContext = ContextThemeWrapper(requireContext(), R.style.Theme_Custom_FilterSelection)
val selectorView = ListSelectionView(newContext)
                  ...
                  .build()
```

## 2. Выбор одного/нескольких значений из перечня тегов

`TagLayoutView` - view-контейнер для тегов
`TagView` - view для тега. <em>Кастомная разметка для тега должна содержать в корне `TagView`</em>

### Как использовать
``` kotlin
binding.tagItemLayout
                    .setSpacing(16)
                    .setSelectionType(SelectionType.MULTI_SELECT) // по умолчанию
                    .isSingleLine(false)    // по умолчанию
                    .onPropertySelectedAction { filterProperty: FilterProperty ->
                        //Do something
                    }
                    .build(getFilterItem())
        }
```
### Конфигурации
* метод `setSelectionType(SelectionType)` конфигурирует тип выбора:
  * `SINGLE_SELECT`  - выбор одного варианта сбрасывает select у всех остальных
  * `MULTI_SELECT` - <em>по умолчанию</em> - мультивыбор тегов с учетом исключающих фильтров
* метод `isSingleLine(Boolean)` конфигурирует вид контейнера с тегами: `true` соответствует горизонтальному контейнеру со скроллом
* `setTagLayout(Int)` устанавливает разметку для тега. Если не задано - то используется дефолтная разметка `layout_default_tag.xml`
* `setMaxTagCount(Int)` позволяет ограничить количество отображаемых тегов. По умолчанию ограничения нет.
* `setMoreTagLayout(Int, String)` устанавливает разметку для тега, который отображается для дополнительного тега. Если не указана - то тег не будет создан
* `setSpacing(Int)`, `setSpacingHorizontal(Int`) и мsetSpacingVertical(Int)` можно использовать для настройки расстояния между тегами. По умолчанию - 0
* `onMoreValuesAction(FilterMoreAction)` и `onPropertySelectedAction(PropertySelectedAction)` используются для передачи колбэков на клик по тегу типа "Еще" и обычного тега соответственно
* после вызова конфигурационных методов обязательно необходимо вызать метод `build(FilterItem)`
