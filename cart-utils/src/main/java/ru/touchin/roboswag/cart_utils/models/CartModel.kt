package ru.touchin.roboswag.cart_utils.models

abstract class CartModel<TProductModel : ProductModel> {

    abstract val products: List<TProductModel>

    open val promocodeList: List<PromocodeModel> = emptyList()

    open val availableBonuses: Int = 0
    open val usedBonuses: Int = 0

    val availableProducts: List<TProductModel>
        get() = products.filter { it.isAvailable && !it.isDeleted }

    val totalPrice: Int
        get() = availableProducts.sumOf { it.countInCart * it.price }

    val totalBonuses: Int
        get() = availableProducts.sumOf { it.countInCart * (it.bonuses ?: 0) }

    fun getPriceWithPromocode(): Int = promocodeList
            .sortedByDescending { it.discount is PromocodeDiscount.ByPercent }
            .fold(initial = totalPrice) { price, promo -> promo.discount.applyTo(price) }

    abstract fun <TCart> copyWith(
            products: List<TProductModel> = this.products,
            promocodeList: List<PromocodeModel> = this.promocodeList,
            usedBonuses: Int = this.usedBonuses
    ): TCart

    @Suppress("UNCHECKED_CAST")
    fun <TCart> asCart() = this as TCart
}
