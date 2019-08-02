# recyclerview-adapters

Модуль, расширяющий возможности работы со стандартным `RecyclerView.Adapter`.

### Основные интерфейсы и классы 

`DelegationListAdapter` - базовый класс, наследник от `RecyclerView.Adapter`. 

Конструктор принимает `DiffUtil.ItemCallback` - интерфейс, описывающий как различать элементы в адаптере. Он содержит два абстрактных метода: *areItemsTheSame* - метод, сравнивающий элементы, и *areContentsTheSame* - метод, сравнивающий визуальную составляющую элементов.

Например, возьмем список товаров, у которых есть уникальный *id* и *title*, который может повторяться. В `RecyclerView` отображается только название товара, т.е. *title*. В такой ситуации в методе *areItemsTheSame* нужно будет написать `oldItem.id == newItem.id`, а в методе *areContentsTheSame* - `oldItem.title == newItem.title`. Оба эти метода вычисляются в бэкграунд потоке. 

Методы `getHeadersCount` и `getFootersCount` нужны, когда в списке есть элементы, которые всегда должны быть наверху или внизу. Например, если нужно добавить кнопку после всех элементов.

Управлением элементами списка занимаются делегаты. Они добавляются с помощью метода *addDelegate*. 

`ItemAdapterDelegate` - делегат, который управляет созданием и прикреплением элементов в зависимости от типа элемента.

`PositionAdapterDelegate` - делегат, который управляет созданием и прикреплением элементов, основываясь на позиции элемента в `DelegationListAdapter`.

При реализации делегатов, необходимо описать два метода: 
* *isForViewType* - метод, который говорит делегату, должен ли он управлять соответсвующим элементом; 
* *onBindViewHolder* - метод, который описывает действия при прикреплении элемента к `ViewHolder`. 

### Примеры 

Создание адаптера.

```Kotlin
class SomeAdapter : DelegationListAdapter<Item>(CALLBACK) {

    companion object {
        private val CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = newItem.id == oldItem.id
            override fun areContentsTheSame(oldItem: Item, newItem: Item) = newItem == oldItem
        }
    }

    init {
        addDelegate(HeaderDelegate())
        addDelegate(ItemDelegate())
        addDelegate(BottomDelegate())
    }
    
    // Some logic in your adapter
}
```

Создание делегата.
```Kotlin
class HeaderDelegate(
        private val addAction: () -> Unit
) : PositionAdapterDelegate<RecyclerView.ViewHolder>() {

    override fun isForViewType(adapterPosition: Int): Boolean = adapterPosition == 0

    override fun onCreateViewHolder(parent: ViewGroup) =
            object : RecyclerView.ViewHolder(HeaderItemView()) {}

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            adapterPosition: Int, 
            payloads: MutableList<Any>
    ) = holder.itemView.setOnClickListener { addAction.invoke() }
}
```

### Подключение 
```gralde
implementation project(':recyclerview-adapters')
```
