navigation
====

Модуль содержит классы для организации навигации в приложении.

### Основные интерфейсы и классы

#### Пакет `activities`

`BaseActivity` - абстрактный класс, в котором выполняется логгирование с помощью модуля [logging](https://github.com/TouchInstinct/RoboSwag/tree/master/logging) при выполнении некоторых методов. Класс позволяет добавлять новые `OnBackPressedListener` и удалять их с помощью методов *addOnBackPressedListener* и *removeOnBackPressedListener* (*removeAllOnBackPressedListeners*) соответственно.

Интерфейс `OnBackPressedListener` - интерфейс с одним методом *onBackPressed*. Используется в `BaseActivity`.

#### Пакет `fragments`

Класс `ViewControllerFragment` наследуется от `Fragment`. Через статический метод *args* получается `Bundle` с классом `ViewController`(а) и состоянием, которое наследуется от `Parcelable`. В методе *onCreate* инициализируются поля *state* и *viewControllerClass* используя данные из `Bundle`. В методе *onCreateView* создается `ViewController`.

#### Пакет `viewcontrollers`

`ViewController` - обертка над Fragment. Один ViewController - один экран. К моменту инициализации вашего класса уже будут доступны следующие поля из `ViewController`: *state*, *activity*, *fragment*, *view*. Это означает, что можно выполнять всю настройку экрана в `init { }`.

У класса есть два параметра `TActivity: FragmentActivity` и `TState: Parcelable`, которые нужно указывать при инициализации класса `ViewController`. В конструкторе данный класс принимает `CreationContext` и идентификатор layout-ресурса.

`ViewControllerNavigation` отвечает за навигацию по `ViewController`(ам). В конструкторе принимает `Context`, `FragmentManager` и идентификатор ресурса, который является контейнером для других фрагментов. Имеет параметр `TActivity : FragmentActivity`.

`EmptyState` - пустое состояние. Использутся, когда при переходе к новому `ViewController` не нужно передавать никаких инициализирующих данных.

`LifecycleLoggingObserver` - подписывается на вызовы методов жизненного цикла и логгирует номер строки из, которой был вызваны эти методы.

Методы для навигации:

* *pushViewController* добавляет `ViewController` в стек. Имеет два обязательных параметра *viewControllerClass* - класс, унаследованный от `ViewController` и *state* - объект описывающий состояние.

* *pushViewControllerForResult* аналогичен предыдущему методу, используется, когда необходимо запустить какой-то фрагмент и при его завершении получить код. Для этого передаются еще два параметра: *requestCode* - код, который нужно получить при закрытии фрагмента и *targetFragment* - фрагмент, который должен получить этот код.

* *setViewControllerAsTop* работает так же как и *pushViewController* но еще добавляет в качестве *backStackName* тег `TOP_FRAGMENT_TAG_MARK`. При выполнении возврата с помощью метода `up` будет выполнен возврат данному фрагменту.

* *setInitialViewController* очищает стек и добавляет туда переданный `ViewController`.

`ViewControllerNavigation` является наследником класса `FragmentNavigation` и для возвратов необходимо использоать методы из родительского класса:

* *back* - вернуться к фрагменту, который лежит ниже в стеке.
* *up* - вернуться к самому низу стека, если в стеке нет фрагментов, помеченных тегом `TOP_FRAGMENT_TAG_MARK`. Если есть, то выполнить возврат к нему. Имеет два необязательных параметра: *name* - имя класса до которого нужно сделать возврат, если он не будет найден, то будет произведен возврат к самому низу стека; *inclusive* - если установить этот флаг, то будет произведен возврат к самому низу стека несмотря на фрагменты с тегом `TOP_FRAGMENT_TAG_MARK`. Если будет установлен и *name* и *inclusive*, то будет произведен возврат к фрагменту, который стоит ниже фрагмента с переданным *name*.

### Примеры

Файл `MainActivity.kt`
```Kotlin
class MainActivity : BaseActivity() {

    private val screenNavigation by lazy {
        ViewControllerNavigation<MainActivity>(
            this,
            supportFragmentManager,
            R.id.fragment_container
        )
    }

    fun getNavigation() = screenNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenNavigation.setInitialViewController(
                MainViewController::class.java,
                MainScreenState(true)
        )
    }

}
```

Файл `MainViewController.kt`
```Kotlin
class MainViewController(
        creationContext: CreationContext
) : ViewController<MainActivity, MainScreenState>(
        creationContext,
        R.layout.view_controller_main
) {

    private val button: View = findViewById(R.id.view_controller_main_button)

    init {
        button.setOnClickListener {
            activity.getNavigation().pushViewController(
                TutorialViewController::class.java,
                EmptyState
            )
        }
    }

}
```

Файл `activity_main.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</FrameLayout>
```

### Рекомендации

Рекомендуется делать состояния, которые передаются во `ViewController` неизменяемыми, чтобы при навигации обратно `ViewController` корректно восстанавливались с изначально заданным состоянием.

### Зависимости

Для работы с данным модулем необходимо так же подключить модуль [logging](https://github.com/TouchInstinct/RoboSwag/tree/master/logging).

```gradle
implementation project(':logging')
```

### Подключение

```gradle
implementation project(':navigation')
```
