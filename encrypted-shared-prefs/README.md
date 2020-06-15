Encrypted shared preferences
============================

Модуль с реализацией интерфейса `SharedPreferences`, который дает возможность шифровать содержимое.

### Пример

Пример получения экземпляра `TouchinSharedPreferences`. При isEncryption = false, `TouchinSharedPreferences` абсолютно аналогичны стандартной реализации `SharedPreferences`

```kotlin
val prefs = TouchinSharedPreferences(name = "APPLICATION_DATA_ENCRYPTED", context = context, encrypt = true)
```

Важно помнить, что в одном файле `TouchinSharedPreferences` могут храниться только либо полностью зашифрованные данные, либо полностью незашифрованные. Флаг `isEncryption` должен быть в соответствующем положении