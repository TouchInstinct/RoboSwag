package ru.touchin.roboswag.cart_utils.models

abstract class CartModel<TProductModel : ProductModel> {

    abstract val products: List<TProductModel>

    open val promocodeList: List<PromocodeModel> = emptyList()

    val availableProducts: List<TProductModel>
        get() = products.filter { it.isAvailable && !it.isDeleted }

    val totalPrice: Int
        get() = availableProducts.sumOf { it.countInCart * it.price }

    val priceWithPromocode: Int
        get() = promocodeList
                .sortedByDescending { it.discount is PromocodeDiscount.ByPercent }
                .fold(initial = totalPrice) { price, promo -> promo.discount.applyTo(price) }

    abstract fun <TCart> copyWith(
            products: List<TProductModel> = this.products,
            promocodeList: List<PromocodeModel> = this.promocodeList
    ): TCart

    @Suppress("UNCHECKED_CAST")
    fun <TCart> asCart() = this as TCart
}
