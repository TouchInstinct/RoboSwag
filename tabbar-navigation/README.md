tabbar-navigation
===

Модуль, упрощающий добавление таббара с обособленной навигацией в каждой табе. Данный модуль базируется на классах модуля [navigation](https://github.com/TouchInstinct/RoboSwag/tree/master/navigation) – `NavigationActivity`, `ViewControllerNavigation`, `ViewController`.

### Основные интерфейсы и классы

`BottomNavigationActivity` – абстрактный класс, наследуемый от класса `NavigationActivity` и содержащий в себе 2 объекта:
* `navigation` – объект, используемый для навигации на уровне `Activity`. При работе с навигацией используя этот объект таббар скрывается на открываемом экране, и переключиться на какую-нибудь другую табу нельзя.
* `innerNavigation` – объект, используемый для навигации вннутри табы. При работе с навигацией используя этот объект таббар остается видимым на открываемом экране, таким образом сохраняя возможность переключаться по остальным табам таббара.

`BottomNavigationFragment` – абстраткный класс, наследуемый от `Fragment` и содержащий в себе логику по настройке навигации используя вложенный объект `BottomNavigationController`.

#### Последовательность необходимых действий для организации навигации:

1. Отнаследовать главную `Activity` приложения от `BottomNavigationActivity` и переопределить следующие поля:

    *  `fragmentContainerViewId` – идентификатор `View` корневого контейнера фрагментов главной `Activity`

2. Отнаследовать контейнерный `Fragment` приложения от BottomNavigationFragment и переопределить следующие поля:
    * `rootLayoutId` – идентификатор `Layout` корневого фрагмента.
    * `navigationContainerViewId` – идентификатор `View` контейнера таббара внутри `Layout` корневого фрагмента.
    * `contentContainerViewId` – идентификатор `View` контейнера содержимого каждой табы внутри `Layout` корневого фрагмента.
    * `contentContainerLayoutId` – идентификатор `Layout` контейнера содержимого каждой табы.
    * `topLevelViewControllerId` – идентификатор `View` кнопки главной табы внутри `View` контейнера таббара.
    * `wrapWithNavigationContainer` – параметр, отвечающий за необходимость добавления обособленной навигации в каждой табе. Если он `false`, то в приложении будет навигация только на уровне главной `Activity`.
    * `navigationViewControllers: SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>` – `SparseArray` с идентификаторами `View` кнопок каждой табы в качестве ключей и пары `ViewControllerClass to ViewControllerState` в качестве значений.

        ***Количество кнопок в таббаре может быть произвольным***

#### Дополнительно
1. В классе ```BottomNavigationFragment``` можно также переопределить поле ```reselectListener```, отвечающее за повторное нажатие на уже открытую табу. По умолчанию происходит переход к низу стека фрагментов открытой табы – ```getNavigationActivity().innerNavigation.up()```

2. Объекты ```innerNavigation``` в классе ```BottomNavigationActivity``` и ```navigation``` в классе ```NavigationActivity``` помечены как ```open```, давая тем самым возможность добавить кастомную логику переопределив их.

3. В случае, если открыта любая таба кроме главной (```topLevelViewControllerId```) со значением ```backStackEntryCount = 0```, нажатие на системную кнопку "Назад" предварительно откроет главную табу, прежде чем отработает действие кнопки по умолчанию.

### Примеры

Файл `MainActivity.kt`

```Kotlin
class MainActivity : BottomNavigationActivity() {

    override val fragmentContainerViewId = R.id.fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        if (savedInstanceState == null) {
            navigation.setInitial(MainFragment::class.java)
        }
    }

}
```

Файл `MainFragment.kt`

```Kotlin
class MainFragment : BottomNavigationFragment() {

    override val rootLayoutId = R.layout.fragment_main

    override val navigationContainerViewId = R.id.navigation_tabs_container

    override val contentContainerViewId = R.id.fragment_container

    override val contentContainerLayoutId = R.layout.fragment_container

    override val topLevelViewControllerId = R.id.navigation_main

    override val wrapWithNavigationContainer = true

    override val navigationViewControllers = SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>().apply {
        put(R.id.navigation_main, MainViewController::class.java to MainState())
        put(R.id.navigation_second, SecondViewController::class.java to SecondState())
        put(R.id.navigation_third, ThirdViewController::class.java to ThirdState())
    }

}
```

Файл `MainViewController.kt`

```Kotlin
class MainViewController(
        creationContext: CreationContext
) : ViewController<MainActivity, MainState>(
        creationContext,
        R.layout.view_controller_main
) {

    private val navigateInsideTabbarButton: View = findViewById(R.id.view_controller_main_button_inside)

    private val navigateOutsideTabbarButton: View = findViewById(R.id.view_controller_main_button_outside)

    init {
        //navigate using innerNavigation and saving tabbar visible
        navigateInsideTabbarButton.setOnClickListener {
            activity.innerNavigation.pushViewController(
                    TutorialViewController::class.java,
                    EmptyState
            )
        }

        //navigate using navigation and making tabbar invisible
        navigateOutsideTabbarButton.setOnClickListener {
            activity.navigation.pushViewController(
                    TutorialViewController::class.java,
                    EmptyState
            )
        }
    }

}
```

### Зависимости

Поскольку модуль базируется на классах модуля [navigation](https://github.com/TouchInstinct/RoboSwag/tree/master/navigation), то необходимо подключить его.

```gradle
implementation project(':navigation')
```

### Подключение

```gradle
implementation project(':tabbar-navigation')
```
