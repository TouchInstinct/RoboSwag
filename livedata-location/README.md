livedata-location
=====

Модуль позволяющий получать местоположение пользователя в виде потока данных `LiveData`.

### Основный интерфейсы и классы

Класс `LocationLiveData`. В конструкторе принимает `Context` и `LocationRequest`. Посылает `Location` подписчикам через указанные в `LocationRequest` интервалы времени. Метод *observe* позволяет подписаться на эти обновления. Данный метод принимает `LifecycleOwner` и `Observer`. Стоит учесть, что для использования данного класса нужно одно из следующих разрешений `ACCESS_COARSE_LOCATION` или `ACCESS_FINE_LOCATION`.
### Примеры

Во `ViewModel`.

```kotlin
val locationWithInterval = LocationLiveData(
        context,
        LocationRequest
                .create()
                .setInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
)
```

Во `ViewController`.

```kotlin
viewModel.locationWithInterval.observe(this, Observer(::onLocationChanged))
```

### Подключение

``` gradle
implementation project(':livedata-location')
```
