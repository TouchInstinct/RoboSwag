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
    * `navigationViewControllers: SparseArray<Pair<Class<out ViewController<*, *>>, Parcelable>>` – `SparseArray` с идентификаторами `View` кнопок каждой табы в качестве ключей и пары `ViewControllerClass to ViewControllerState` в качестве значений.

        ***Количество кнопок в таббаре может быть произвольным***

### Интерфейс для работы с таббаром

Для навигации используются объекты-наследники класса `ViewControllerNavigation`

1. Для навигации внутри табы необходимо использовать объект  ```activity.innerNavigation```. При использовании этого метода таббар остается видимым на открываемом экране, таким образом сохраняя возможность переключаться по остальным табам таббара.
2. Для навигации на уровне ```Activity``` необходимо использовать объект  ```activity.navigation```. При использовании этого метода таббар скрывается на открываемом экране, и переключиться на какую-нибудь другую табу нельзя.

### Примеры

Файл `MainActivity.kt`

```Kotlin
class MainActivity : BottomNavigationActivity() {

    override val fragmentContainerViewId: Int = R.id.fragment_container

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

### Дополнительно

1. В классе ```BottomNavigationFragment``` можно также переопределить поле ```val reselectListener: (() -> Unit)```, отвечающее за повторное нажатие на уже открытую табу. По умолчанию происходит переход к низу стека фрагментов открытой табы – ```getNavigationActivity().innerNavigation.up()```

2. Объекты ```innerNavigation``` в классе ```BottomNavigationActivity``` и ```navigation``` в классе ```NavigationActivity``` помечены как ```open```, давая тем самым возможность добавить кастомную логику переопределив их.

3. В случае, если открыта любая таба кроме главной (```topLevelViewControllerId```) со значением ```backStackEntryCount = 0```, нажатие на системную кнопку "Назад" предварительно откроет главную табу, прежде чем отработает действие кнопки по умолчанию.

