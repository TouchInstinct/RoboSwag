lifecycle-rx
=====

Модуль для автоматического управления подписками.

Destroyable - интерфейс, содержит функцию-расширение для Flowable, Observable, Single, Completable, Maybe: `untilDestroy`.
Данная функция гарантирует, что на Observable (и другие его формы, перечисленные ранее) никто не подпишется, после onDestroy.

BaseDestroyable - базовая реализация Destroyable.

LifeDataDispatcher - интерфейс, описывающий поведение прикрепления событий из Flowable, Observable, Single, Maybe к
MutableLiveData<ContentEvent<T>> и из Completable к MutableLiveData<Event>.

RxViewModel - базовый класс, от которого должны наследоваться все ViewModel. Обеспечивает отписку всех подписчиков при возникновении
onCleared, из ViewModel. Реализует BaseDestroyable и LiveDataDispetcher, при желаниее можно передать свои Destroyable и LiveDataDispatcher через конструктор.

Пример ViewModel на базе RxViewModel
[source, kotlin]
----
class SomeViewModel (
        private val someRepository: SomeRepository
) : RxViewModel() {

    val thingsList = MutableLiveData<ContentEvent<List<Thing>>>()

    fun getThingsList() {
        someRepository
                .getThings()
                .dispatchTo(thingsList)
    }

    fun postThing(thing: Thing) {
        someRepository
                .postThing(thing)
                .untilDestroy()
    }

}
----
