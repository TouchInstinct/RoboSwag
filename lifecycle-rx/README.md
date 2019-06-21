lifecycle-rx
=====

Модуль для преобразования событий из `Flowable`, `Observable`, `Single`, `Completable`, `Maybe` в `LiveData`. Нужен для передачи
событий из `ViewModel` во `ViewController` с автоматическим управлением подписками во `ViewController`.

### Основный интерфейсы и классы
`Destroyable` - интерфейс, содержит функцию-расширение для `Flowable`, `Observable`, `Single`, `Completable`, `Maybe`: 
*untilDestroy*.
Данная функция гарантирует, что на `Observable` 
(и другие его формы, перечисленные ранее) никто не подпишется после *onDestroy*.

`LifeDataDispatcher` - интерфейс, описывающий функцию *dispatchTo* для преобразования `Observable` в `MutableLiveData` для передачи во `ViewController`. 

`BaseDestroyable` и `BaseLifeDataDispatcher` - базовые реализации `Destroyable` и `LifeDataDispatcher` соответсвенно.

`RxViewModel` - базовый класс, от которого должны наследоваться все `ViewModel`. Обеспечивает отписку всех подписчиков при возникновении
*onCleared*. Реализует `BaseDestroyable` и `LiveDataDispetcher`. По умолчанию использует базовые реализации данных интерфейсов, 
при желаниее можно передать свои `Destroyable` и `LiveDataDispatcher` через конструктор.

### Примеры

Простой пример `ViewModel`, через который можно получить список "вещей" и отправить какую-то "вещь".
функция *getThingsList* преобразует событие из мира `Rx` в `LiveData` с помощью *dispatchTo(thingsList)*. 

```kotlin
class SomeViewModel (
        private val someRepository: SomeRepository
) : RxViewModel() {

    val thingsList = MutableLiveData<ContentEvent<List<Thing>>>()

    fun getThingsList() {
        someRepository
                .getThings()
                .dispatchTo(thingsList)
    }

    fun postThing(thing: Thing) {
        someRepository
                .postThing(thing)
                .untilDestroy()
    }
}
```

Подписка на события во `ViewController`.

```kotlin
someViewModel.thingsList.observe(this, Observer { event ->
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
