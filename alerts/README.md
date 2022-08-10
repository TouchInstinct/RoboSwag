alerts
=====

### Общее описание

Модуль содержит: 
`ViewableAlertDialog` - служит для демонстрации AlertDialog с использованием View, необходимо вызвать метод `showAlertDialog`, который 
в качестве агруметов может принимать:
* title - Заголовок диалога
* message - дополнительное сообщение
* positiveButtonText - текст правой кнопки (по умолчанию "ОК") 
* onPositiveAction - колбэк при нажатии на правую кнопку
* negativeBtnTitle - текст левой кнопки (по умолчаннию null - в этом случаи не отображается)
* onNegativeAction - колбэк при нажатии на левую кнопку,
* dialogLayout - id кастомного layout (по умолчанию R.layout.dialog_alert)

---
`ComposableAlertDialog` - служит для демонстрации AlertDialog с использованием Jetpack Compose, необходимо вызвать метод `ShowAlertDialog`, который 
в качестве агруметов может принимать:
* isDialogOpen - индикатор состояния диалога
* title - Заголовок диалога
* message - дополнительное сообщение
* positiveButtonText - текст правой кнопки
* onPositiveAction - колбэк при нажатии на правую кнопку
* negativeBtnTitle - текст левой кнопки (по умолчаннию null - в этом случаи не отображается)
* onNegativeAction - колбэк при нажатии на левую кнопку,

Кастомизация Compose версии происходит по средствам инициализации полей: customTitle, customMessage, customConfirmBtn, customNegativeBtn

### Примеры

View версия (ViewableAlertDialog):
```kotlin
ViewableAlertDialog.showAlertDialog(
                activity,
                title = "Ой, что-то пошло не так",
                message = "Попробуйте ещё раз",
                positiveButtonText = "Ещё раз",
                onPositiveAction = { retryConnection() }, 
                negativeBtnTitle = "Отмена"
            )
```

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