recaptcha
=====

### Общее описание

Модуль содержит класс `CaptchaManager` - служит для проверки используемого сервиса (Huawei или Google) и показа диалога с каптчёй
В конструктуре `CaptchaManager` принимает два callback:
`onNewTokenReceived` - успешная проверка, возвращает токен
`processThrowable` - ошибка, возвращает `Throwable`

### Требования

Для использования модуля нужно добавить json файл с сервисами в корневую папку проекта:

1. Для Google - google-services.json
2. Для Huawei - agconnect-services.json

### Пример

Во `Fragment`

```kotlin
val manager = CaptchaManager(onNewTokenReceived = { token ->
        viewModel.sendRequest(token)
    }, processThrowable = { error ->
        showError(error)
    })

manager.showRecaptchaAlert(
    activity = activity,
    captchaKey = BuildConfig.CAPTCHA_TOKEN
)
```
