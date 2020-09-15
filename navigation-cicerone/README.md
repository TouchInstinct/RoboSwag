# navigation-cicerone

Набор базовых классов для настройки навигации на основе библиотеки cicerone

## Основные классы и интерфейсы

### FlowFragment, FlowNavigation, FlowNavigationModule

Для использования Single-Activity подхода возникают ситуации, когда несколько экранов нужно объединить одной родительской сущностью.
Объединение необходимо для хранения общего dagger компонента на flow или организации собственной навигации.
Раньше можно было использовать Activity. Но в Single-Activity должно быть только одно активити на приложение. В такой архитектуре можно использовать
parent fragment для всех экран - FlowFragment. FlowFragment обладает своей навигацией. Для навигации используется childFragmentManager этого фрагмента.

Чтобы использовать эту навигацию в di, в модуль добавлены dagger-модуль FlowNavigationModule, который держит основные cicerone сущности, и именная
аннотация FlowNavigation.

### CiceroneTuner

CiceroneTuner наследует класс LifecycleObserver для упрощенной работы с NavigatorHolder. Данный класс в onResume добавляет navigator в NavigatorHolder
и в onPause удаляет navigator из NavigatorHolder.

## Дополнительные материалы

- [Статья, которая объясняет Single-Activity подход](https://habr.com/ru/company/redmadrobot/blog/426617/)
