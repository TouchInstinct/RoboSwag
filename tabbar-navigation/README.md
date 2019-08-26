tabbar-navigation
===

Модуль, упрощающий добавление таббара с обособленной навигацией в каждой табе. Данный модуль базируется на классах модуля `navigation` – `NavigationActivity`, `ViewControllerNavigation`, `ViewController`. 

### Подключение

```gradle
implementation project(':tabbar-navigation')
```
### Последовательность необходимых действий с основными классами для организации навигации:

1. Отнаследовать главную `Activity` приложения от `BottomNavigationActivity` и переопределить следующие поля:

    *  `fragmentContainerViewId: Int` – идентификатор `View` корневого контейнера фрагментов главной `Activity`

2. Отнаследовать контейнерный `Fragment` приложения от BottomNavigationFragment и переопределить следующие поля:
    * `rootLayoutId: Int` – идентификатор `Layout` корневого фрагмента.
    * `navigationContainerViewId: Int` – идентификатор `View` контейнера таббара внутри `Layout` корневого фрагмента.
    * `contentContainerViewId: Int` – идентификатор `View` контейнера содержимого каждой табы внутри `Layout` корневого фрагмента.
    * `contentContainerLayoutId: Int` – идентификатор `Layout` контейнера содержимого каждой табы.
    * `topLevelViewControllerId: Int` – идентификатор `View` кнопки главной табы внутри `View` контейнера таббара.
    * `wrapWithNavigationContainer: Boolean` – параметр, отвечающий за необходимость добавления обособленной навигации в каждой табе. Если он `false`, то в приложении будет навигация только на уровне главной `Activity`.
    * `navigationViewControllers: SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>` – `SparseArray` с идентификаторами `ViewController` кнопок каждой табы в качестве ключей и пары `ViewControllerClass to ViewControllerState` в качестве значений.

        ***Количество кнопок в таббаре может быть произвольным***

