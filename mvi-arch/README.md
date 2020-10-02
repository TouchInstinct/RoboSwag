# mvi_arch

Модуль для реализации presentation слоя с помощью паттерна mvi.

Модуль содержит две реализации mvi: упрощенная и полная.

## Упрощенная реализация через MviViewModel

### Отличия от стандартного ViewModel

У MviViewModel есть один метод для обработки внешних событий пользователя - dispatchAction.
События должны наследоваться от интерфейса-маркера ViewAction. Также у MviViewModel есть
livedata, содржащую в себе single state экрана, который наследуется от интерфейса-маркера ViewState. 
То есть у MviViewModel есть один вход и один выход.

### MviViewModel и AssistedInject

MviViewModel использует AssistedInject в конструктор для получения inititalState через конструктор 
и механизм для сохранения данных экрана в Bundle - SavedStateHandle.
AssistedInject используется для передачи navArgs из фрагмента, который подключает ViewModel.

### MviFragment

Главная особенность MviFragment - связь с MviViewModel. Получить экземпляр класса MviViewModel можно через
lazy делегата - viewModel().

## Полная реализация через MviStoreViewModel

### Store

Store - реализация стора из Redux. Он держит в себе стейт машину, у которой могут быть SideEffect. Сайд эффекты обрабатываются отдельно
в одном kotlin flow.

### MviStoreViewModel

MviStoreViewModel наследуется от MviViewModel. Единственная ценность этого класса - он хранит в себе список Store, передает Action внутрь Store,
комбинирует стейты всех сторов в один большой стейт.

### Почему всегда не использовать MviStoreViewModel

Реализация полного MVI требует много кода. Для простых экранов, то есть для 85 процентов экранов рекомендуется использовать MviViewModel.

## Дополнительные материалы

- [Доклад про MVI от Сергея Рябова](https://youtu.be/hBkQkjWnAjg)
- [Доклад про эволюцию презентационных паттернов](https://youtu.be/J0YPKcDKumk)
- [Доклад про расширяемую архитектуру в Lyft](https://www.youtube.com/watch?v=_cFHtjIWjCc)
- [Презентация доклада со внутреннего митапа](https://docs.google.com/presentation/d/1gBg8n8xAyIytDo1-L9GvrCkrM6AFynLPYKcsQ_NPs7k/edit#slide=id.p)

