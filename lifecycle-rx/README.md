lifecycle-rx
=====

Модуль для преобразования событий из `Flowable`, `Observable`, `Single`, `Completable`, `Maybe` в `LiveData`. Нужен для передачи событий из `ViewModel` во `ViewController` с автоматическим управлением подписками во `ViewController`.

### Основный интерфейсы и классы
`Destroyable` - интерфейс, который содержит extansion-функцию *untilDestroy* для `Flowable`, `Observable`, `Single`, `Completable`, `Maybe`. Данная функция гарантирует, что все подписка на события "умрет" после *onDestroy*.

`LifeDataDispatcher` - интерфейс, описывающий функцию *dispatchTo* для преобразования `Observable` в `MutableLiveData`. 

`BaseDestroyable` и `BaseLifeDataDispatcher` - базовые реализации `Destroyable` и `LifeDataDispatcher` соответсвенно.

`RxViewModel` - базовый класс, от которого должны наследоваться все `ViewModel`. Обеспечивает отписку всех подписчиков при возникновении *onCleared*. Реализует `BaseDestroyable` и `LiveDataDispatcher`. По умолчанию использует базовые реализации данных интерфейсов, при желании можно передать свои `Destroyable` и `LiveDataDispatcher` через конструктор.

### Примеры

Простой пример `ViewModel`, через который можно получить список элементов и добавить один элемент.

```kotlin
class SomeViewModel (
        private val someRepository: SomeRepository
) : RxViewModel() {

    val itemsList = MutableLiveData<ContentEvent<List<Item>>>()

    fun getItemsList() {
        someRepository
                .getItems()
                .dispatchTo(itemsList)
    }

    fun addItem(item: Item) {
        someRepository
                .addItem(item)
                .untilDestroy()
    }
}
```

Подписка на события во `ViewController`. `ContentEvent` описан в модуле [lifecycle-rx](https://github.com/TouchInstinct/RoboSwag/tree/master/lifecycle-rx).

```kotlin
someViewModel.itemsList.observe(this, Observer { event ->
    when (event) {
        is ContentEvent.Loading -> // do something
        is ContentEvent.Success -> // do something
        is ContentEvent.Error -> // do something
    }
})
```

### Подключение

``` gradle
implementation project(':lifecycle-rx')
```
