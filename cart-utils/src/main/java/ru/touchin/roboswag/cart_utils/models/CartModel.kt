package ru.touchin.roboswag.cart_utils.models

abstract class CartModel<TProductModel : ProductModel> {

    abstract val products: List<TProductModel>

    val availableProducts: List<TProductModel>
        get() = products.filter { it.isAvailable && !it.isDeleted }

    val totalPrice: Int
        get() = availableProducts.sumOf { it.price }

    abstract fun <TCart> copyWith(
            products: List<TProductModel> = this.products,
    ): TCart

    @Suppress("UNCHECKED_CAST")
    fun <TCart> asCart() = this as TCart
}
