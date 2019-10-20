#  RoboSwag
Roboswag - библиотека решений, ускоряющих разработку Android приложений. Она включает в себя архитектурные решения для построения приложения, утилитарные классы и общие инструменты, которые используются в компании Touch Instinct. 
Библиотека состоит из gradle модулей. Каждый модуль отвечает за свой функционал. В проектах используются только те модули, которые нужны. Такая модульность позволяет  сохранять размер приложения небольшим и ускорять сборку проекта.

## Минимальные требования

* Andoroid Api: 19
* Kotlin: 1.3.11
* Gradle: 3.2.1
* Gradle CPD Plugin: 1.1
* Detekt Plugin: 1.0.0-RC12

## Основная архитектура
За основу архитектуры взят подход от Google - MVVM на основе [Android Architecture Components](https://developer.android.com/jetpack/docs/guide). Данный подход популярен в сообществе Android разработки, позволяет разбивать код на мелкие и независимые части, что ускоряет разработку и последующую поддержку приложения.

Для организации многопоточности используется фреймворк [RxJava2](https://github.com/ReactiveX/RxJava). RxJava -  обширный инструмент, реализующий концепции реактивного программирования. Сочетание этой концепции с возможностью выносить задачи на другой поток позволяет легко писать многопоточное асинхронное приложение.

В качестве Di-фреймворка выбран [Dagger 2](https://github.com/google/dagger). Он позволяет сделать код приложения менее связным, более гибким и позволяет легко настроить автотестирование.

Roboswag позволяет сочетать эти три решения в одну гибкую и удобную архитектуру. Разработка становится быстрее, проще и надежнее. За архитектуру отвечают модули [lifecycle](/lifecycle) и [lifecycle-rx](/lifecycle-rx).

## Основные инструменты библиотеки
### Работа с RecyclerView
RecyclerView - один из самых часто используемых инструментов Android разработчика. Модуль [recyclerview-adapters](/recyclerview-adapters) позволяет сделать работу с RecyclerView более гибкой и делает работу самого элемента быстрее.
### BuildScripts
[BuildScrpts](https://github.com/TouchInstinct/BuildScripts) - набор скриптов, автоматизирующих разработку. Один из главных скриптов - staticAnalysis - инструмент для автоматической проверки кода на соответствие правилам компании. 
### Api Generator
Внутренний инструмент компании Touch Instinct для генерации общего кода на разные платформы - Android, iOS и Server. Описанные в одном месте общие классы и Http методы используются на разных платформах. Данный инструмент позволяет сократить время разработки в два раза.
### Работа с SharedPreferences
Чтобы сохранять простые данные в память смартфона, используются SharedPreferences. Модуль [storable](/storable) разработан для облегчения работы с SharedPreferences.
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
        'google-map'
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

What is a RecyclerView?
A RecyclerView is essentially a ViewGroup of containers called ViewHolders which populate a particular item. RecyclerView is an extensive and exhaustive Android class to provide a flexible UI. A huge benefit of using RecyclerView is that you’re able to is efficiently reuse views instead of managing items that aren’t even visible to a user. You can think of those containers as a way to recycle the population of those view instances.

Every RecyclerView requires:
a set of data objects to work with
an adapter to bind that data to the views shown in the ViewHolders
an xml file of the individual view item
a ViewHolder to populate the UI from the xml item file
Now we’ve gotten familiar with what RecyclerView is and its basic mechanics, let’s get into the implementation.
Getting Started
We’re going to implement a list RecyclerView displaying a collection of Nicolas Cage movies and the year the movie was released.. Let’s knock the easiest stuff out of the way first — setting up a RecyclerView in and the individual list item we expect to populate in our ViewHolder. You’ll notice we attached Android ids for the RecyclerView as well as the TextView elements.

Nothing out of the ordinary here.
Initializing RecyclerView in our Fragment with a Data set
We also create our data class object in Kotlin — nothing special here either, just a LOT less boilerplate.
data class Movie(val title: String, val year: Int)
In a previous post, we talked about the SingleFragmentActivity pattern. As a result, I plan on sitting the RecyclerView on the Fragment created for this pattern. We also include a data set to populate our RecyclerView with.

To really appreciate the differences, let us compare a similar Java implementation of RecyclerView to ours in Kotlin:

This implementation of RecyclerView has a different look in Kotlin:
In the Java file, we inflate our layout and initialize our RecyclerView in the same method. In Java, we risk our custom ListAdapter clashing with an existing adapter that may have been spurned from an earlier interaction. A safer implementation in Kotlin is to inflate and return the layout for onCreateView(…) and then returning an immutable implementation of the ListAdapter.
Using apply { } makes our code more readable as we access properties and apply methods to return an object with our LinearLayoutManager and custom ListAdapter
With the Kotlin Extension Android library, we can refer to a node in Android programming with its identifier list_recycler_view, as opposed to initializing the View itself and binding the element you created to that View instance type like you would have to do in Java. Hooray for less code!
Custom ViewHolders
Next, we create our custom ViewHolders, MovieViewHolder, and create a custom Adapter that will bind our data to populated MovieViewHolders in the RecyclerView.

Now that we have our RecyclerView initialized along with the xml file list_item.xml to describe layout of the items we intend to populate in our ViewHolders, we should create our custom ViewHolder object.
In our MovieViewHolder, we inherit behaviors and properties from the ViewHolder object in the RecyclerView class. We choose to save the View elements as a global variable in the class in the future event we choose to do more with our ViewHolder, such as click events. You can choose between a lateinit or making an object nullable in Kotlin, but I try to stay away from lateinit, as there is an implicit agreement that you, the developer, promises that the object will be initialized by the time it is being used. This is one annoyance I have with Kotlin in Android development, so I stick with nullability for the time being. For that reason, mTitleView and mYearView have the accessible property text, but to prevent NPEs for these Views, a null-safety check ?. is added to the object itself.

In our ListAdapter, we inherit expected methods from the Adapter object in the RecyclerView class onCreateViewHolder(…), onBindViewHolder(…), and getItemCount().
When the Adapter is initialized, it will populate MovieViewHolders in the RecyclerView with the onCreateViewHolder(…) method. The method onBindViewHolder(…) will take a data collection and apply a rotating rendering of visible data applied to those ViewHolders.

Surprisingly, that’s it. And that’s all you need to know to create RecyclerViews! You can change out the kind of layout you want to work with for LayoutManager with other managers such as GridLayoutManager. You can substitute your view nodes with images, or any other setup you wish. This implementation is relatively flexible and allows you to keep objects agnostic so that your ingredients can function independently of one another. Until next time!

443


