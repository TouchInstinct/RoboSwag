# pagination

Модуль для добавления списка элементов с постраничной загрузкой.

## Основные классы

### Paginator
Класс наследуется от Store из модуля mvi-arch. Стейт-машина, которая отвечает за изменение состояния списка элементов.

### PaginationView
View, которая отвечает за отображение постраничного списка. Основной метод - render, который принимает на вход Paginator.State. View состоит из 
SwipeRefreshLayout и Switcher на 3 состояния: loading, error/empty, success. Success state состоит из RecyclerView, который работает с PaginationAdapter.

## Дополнительные материалы

- [Доклад, на котором основан модуль](https://www.youtube.com/watch?v=n9mfLWI8ktE)
