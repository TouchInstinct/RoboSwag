recyclerview-adapters
=====

Модуль, расширяющий возможности работы со стандартным `RecyclerView.Adapter`.

### Основные интерфейсы и классы 

`DelegationListAdapter` - базовый класс, который реализует `RecyclerView.Adapter`. Управлением элементами списка занимаются делегаты. 
Они добавляются с помощью метода *addDelegate*. Конструктор принимает `DiffUtil.ItemCallback`, который вычисляется в фоне.
Методы `getHeadersCount` и `getFootersCount` нужны, когда в списке есть элементы, которые всегда должны быть наверху или внизу.
Например, если мы добавляем кнопку в самый низ списка. 

`ItemAdapterDelegate` - делегает, который управляет созданием и прикреплением элементов, основываясь на типе элемента.

`PositionAdapterDelegate` - делегает, который управляет созданием и прикреплением элементов, основываясь на позиции элемента, в 
`DelegationListAdapter`

При реализации делегатов, необходимо описать два метода: 
* *isForViewType*, который говорит делегату, должен ли он управлять
соответсвующим элементом; 
* *onBindViewHolder* - действия при прикреплении элемента к `RecyclerView`. 

### Примеры 

Создание адаптера.

```Kotlin
class SomeAdapter : DelegationListAdapter<Item>(CALLBACK) {

    companion object {
        private val CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = // Checks
            override fun areContentsTheSame(oldItem: Item, newItem: Item) = // Checks
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
            adapterPosition: Int, payloads: 
            MutableList<Any>
    ) = holder.itemView.setOnClickListener { addAction.invoke() }
}
```

### Подключение 
```gralde
implementation project(':recyclerview-adapters')
```
