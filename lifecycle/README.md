lifecycle
=====

Модуль содержит обертку над `ViewModelProviders` для работы с `ViewController` и обертки для передачи событий из `ViewModel` во `ViewController`.

### Основные интерфейсы и классы

`LifecycleViewModelProviders` - объект для получения `ViewModelProvider`. Содержит функцию *of*, которая принимает `LifecycleOwner` и возвращает специфичный для него `ViewModelProvider`. 

`SingleLiveEvent` - событие - одиночка. Посылает события только один раз. Наследуется от `MutableLiveData` и переопределяет методы `observe` и `setValue`.

`ContentEvent` - событие, обертка над данными. 
Дочерние классы: 
* `Loading` - символизирует состояние загрузки, 
* `Success` - символизирует успешное событие, 
* `Error` - символизирует ошибку, 
* `Complete` - символизирует завершение события. 

`Event` - аналогичен `ContentEvent`, только не содержит никакой информации о данных. Нужен для оповещения о наступлении одного из следующих событий: `Loading`, `Complete` или `Error`.

### Примеры

Получение `ViewModel` во `ViewController`.

```kotlin
private val viewModel = LifecycleViewModelProviders.of(this).get(SomeViewModel::class.java)
```

Подписка на `SingleLiveEvent`.

```kotlin
// во ViewModel
val event = SingleLiveEvent<Event>()

// во ViewController
event.observe(this, Observer { event ->
    when (event) {
        is Event.Loading -> ::onEventLoading
        is Event.Complete -> ::onEventComplete
        is Event.Error -> ::onEventError
    }
})
```

### Подключение

```gradle
implementation project(':lifecycle')
```
