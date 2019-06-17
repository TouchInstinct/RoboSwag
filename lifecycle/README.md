lifecycle
=====

Модуль содержит классы, упрощающие процесс подписки на события.

Usage
=====

`LifecycleViewModelProviders` - объект для получения ViewModelProvider. В методе `of` принимает LifecycleOwner. Будет хранить ссылку на viewModel, пока существует LifecycleOwner.

[source, kotlin]
----
// во ViewController
private val viewModel = LifecycleViewModelProviders.of(this).get(SomeViewModel::class.java)
----

`SingleLiveEvent` - событие - одиночка. Посылает только новые события, возникшие после подписки. Наследуется от `MutableLiveData` и переопределяет методы `observe` и `setValue`.

Зачем может понадобится `SingleLiveEvent`. Допустим, у вас есть `ViewController`, в котором через `ViewModel` вы делаете запрос в сеть.
В этом же `ViewController` вы подписываетесь на `SingleLiveEvent` из `ViewModel`. При получении ответа, нужно перейти на новой `ViewController`.

[source, kotlin]
----
// во ViewModel
val event = SingleLiveEvent<Event>()

// во ViewController
event.observe(this, Observer { event ->
    when (event) {
        is Event.Loading -> ::onEventLoading
        is Event.Complete -> ::onEventComplete
        is Event.Error -> ::onEventError
    }
})
----

`ContentEvent` - событие, обертка над данными, sealed - класс. Дочерние классы: Loading - возникает сразу после прикрепления через
`dispatchTo`, Success - возникает как успешное событие, Error - при возникновении ошибки, Complete - при завершении события.
Используется в BaseLiveDataDispatcher в модуле lifecycle-rx.
`Event` - аналогичен `ContentEvent`, только не содержит никакой информации о данных. Нужен для оповещения о наступлении одного из следующих событий:
`Loading`, `Complete` и `Error`.