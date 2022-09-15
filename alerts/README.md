Alerts
=====

### Общее описание

Модуль содержит: 
`AlertDialogManager` - служит для демонстрации AlertDialog с использованием View, необходимо вызвать метод `showAlertDialog`, который 
в качестве агруметов может принимать:
* `context`,
* `style` - стиль для элементов дефолтного диалога (по умолчанию R.style.AlertDialogDefault),
* `title` - Заголовок диалога,
* `message` - дополнительное сообщение,
* `positiveButtonText` - текст правой кнопки (по умолчанию "ОК") ,
* `onPositiveAction` - колбэк при нажатии на правую кнопку,
* `negativeBtnTitle` - текст левой кнопки (по умолчаннию null - в этом случаи не отображается),
* `onNegativeAction` - колбэк при нажатии на левую кнопку,
* `dialogLayout` - id кастомного layout (по умолчанию R.layout.dialog_alert).

---
`ComposableAlertDialog` - служит для демонстрации AlertDialog с использованием Jetpack Compose, необходимо вызвать метод `ShowAlertDialog`, который 
в качестве агруметов может принимать:
* `isDialogOpen` - индикатор состояния диалога,
* `title` - Заголовок диалога,
* `message` - дополнительное сообщение,
* `positiveButtonText` - текст правой кнопки,
* `onPositiveAction` - колбэк при нажатии на правую кнопку,
* `negativeBtnTitle` - текст левой кнопки (по умолчаннию null - в этом случаи не отображается),
* `onNegativeAction` - колбэк при нажатии на левую кнопку.

Кастомизация Compose версии происходит по средствам инициализации полей: customTitle, customMessage, customConfirmBtn, customNegativeBtn

### Примеры

View версия (ViewableAlertDialog) ok/cancel диалога:
```kotlin
alertDialogManager.showAlertDialog(
        context = activity,
        title = "Ой, что-то пошло не так",
        message = "Попробуйте ещё раз",
        positiveButtonText = "Ещё раз",
        onPositiveAction = { retryConnection() }, 
        negativeBtnTitle = "Отмена"
)
```

View версия (ViewableAlertDialog) ok диалога:
```kotlin
alertDialogManager.showOkDialog(
        context = dialog?.window?.decorView?.context ?: throw Exception(),
        title = "Необходимо изменить настройки",
        okButtonText = "Ок",
        onOkAction = {
            viewModel.dispatchAction(ItemAction.ChangeSettings)
        }
)
```

Для катомизации стилей элементов в дефолтной разметке диалога необходимо создать стиль - наследника от `ThemeOverlay.MaterialComponents.MaterialAlertDialog` и переопределить стили:
* `materialAlertDialogTitleTextStyle` - стиль для заголока (наследник от `MaterialAlertDialog.MaterialComponents.Title.Text`),
* `materialAlertDialogBodyTextStyle` - стиль для подзаголовка (наследник от `MaterialAlertDialog.MaterialComponents.Body.Text`),
* `buttonBarPositiveButtonStyle` - стиль для позитивной кнопки (наследник от `Widget.MaterialComponents.Button.TextButton.Dialog`),
* `buttonBarNegativeButtonStyle` - стиль для негативной кнопки (наследник от `Widget.MaterialComponents.Button.TextButton.Dialog`).

Compose версия (ComposableAlertDialog):
```kotlin
val isDialogOpen = remember { mutableStateOf(false)}
....
//Создание диалога
ComposableAlertDialog
            .apply { customTitle = { Text(text = "Ой, что-то пошло не так", color = Color.Blue) } }
            .ShowAlertDialog(isDialogOpen, message = "Проблемы с сетью", positiveButtonText = "ОК")
....
//Отображение диалога
isDialogOpen.value = true
```
