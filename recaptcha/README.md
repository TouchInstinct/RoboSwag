recaptcha
=====

Модуль содержит класс `RecaptchaController` - служит для проверки используемого сервиса (Huawei или Google) и показа диалога с каптчёй

Для использования модуля нужно добавить json файл с сервисами в корневую папку проекта

### Конструктор
* `googleRecaptchaKey` - ключ для google recaptcha
* `huaweiAppId` - `id` приложения для huawei captcha
* `onNewTokenReceived` - callback на успешную проверку каптчи
* `processThrowable` - callback на ошибку проверки каптчи
