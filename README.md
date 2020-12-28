#  RoboSwag
Roboswag - библиотека решений, ускоряющих разработку Android приложений. Она включает в себя архитектурные решения для построения приложения, утилитарные классы и общие инструменты, которые используются в компании Touch Instinct. 
Библиотека состоит из gradle модулей. Каждый модуль отвечает за свой функционал. В проектах используются только те модули, которые нужны. Такая модульность позволяет  сохранять размер приложения небольшим и ускорять сборку проекта.

## Минимальные требования

* Android Api: 21
* Kotlin: 1.3.11
* Gradle: 4.0.0

## Основная архитектура
За основу архитектуры взят подход от Google - MVVM на основе [Android Architecture Components](https://developer.android.com/jetpack/docs/guide). Данный подход популярен в сообществе Android разработки, позволяет разбивать код на мелкие и независимые части, что ускоряет разработку и последующую поддержку приложения.

Для организации многопоточности используется фреймворк [RxJava2](https://github.com/ReactiveX/RxJava). RxJava -  обширный инструмент, реализующий концепции реактивного программирования. Сочетание этой концепции с возможностью выносить задачи на другой поток позволяет легко писать многопоточное асинхронное приложение.

В качестве Di-фреймворка выбран [Dagger 2](https://github.com/google/dagger). Он позволяет сделать код приложения менее связным, более гибким и позволяет легко настроить автотестирование.

Roboswag позволяет сочетать эти три решения в одну гибкую и удобную архитектуру. Разработка становится быстрее, проще и надежнее. За архитектуру отвечают модули [lifecycle](/lifecycle) и [lifecycle-rx](/lifecycle-rx).

## Основные инструменты библиотеки
### Работа с RecyclerView
RecyclerView - один из самых часто используемых инструментов Android разработчика. Модуль [recyclerview-adapters](/recyclerview-adapters) позволяет сделать работу с RecyclerView более гибкой и делает работу самого элемента быстрее.
### Работа с SharedPreferences
Чтобы сохранять простые данные в память смартфона, используются SharedPreferences. Модуль [storable](/storable) разработан для облегчения работы с SharedPreferences. Для шифрования данных в SharedPreferences можно использовать [encrypted-shared-prefs](/encrypted-shared-prefs)
### Утилиты и extension функции
В Roboswag также есть много [утилитарных](/utils) классов и [extension](/kotlin-extensions) функций, которые позволяют писать часто используемый код в одну строку.

### Подключение

#### .gitmodules

```
[submodule "RoboSwag"]
	path = RoboSwag
	url = git@github.com:TouchInstinct/RoboSwag.git
```

#### build.gradle (Module: app)

```gradle
dependencies {
    gradle.ext.roboswag.forEach { module ->
        implementation project(":$module")
    }
}
```
Управление зависимостями нужно производить с помощью `ext.roboswag` добавляя или удаляя из него названия модулей.
#### settings.gradle (Module: project)

```gradle
gradle.ext.roboswag = [
        'utils',
        'logging',
        'navigation',
        'storable',
        'api-logansquare',
        'lifecycle',
        'views',
        'recyclerview-adapters',
        'kotlin-extensions',
        'recyclerview-calendar',
        'tabbar-navigation',
        'base-map',
        'yandex-map',
        'google-map',
        'webview',
        'encrypted-shared-prefs'
]

gradle.ext.roboswag.forEach { module ->
    include ":$module"
    project(":$module").projectDir = file("RoboSwag/$module")
}
```

### R8/Proguard

```
-keep class ** extends ru.touchin.roboswag.components.navigation.viewcontrollers.ViewController { *; }
```

### Лицензия

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
