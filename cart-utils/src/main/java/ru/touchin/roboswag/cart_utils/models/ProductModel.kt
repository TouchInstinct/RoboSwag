package ru.touchin.roboswag.cart_utils.models

abstract class ProductModel {
    abstract val id: Int
    abstract val countInCart: Int
    abstract val price: Int
    abstract val isAvailable: Boolean
    abstract val isDeleted: Boolean

    open val variants: List<ProductModel> = emptyList()

    abstract fun <TProduct> copyWith(
            countInCart: Int = this.countInCart,
            isDeleted: Boolean = this.isDeleted,
    ): TProduct

    @Suppress("UNCHECKED_CAST")
    fun <TProduct> asProduct(): TProduct = this as TProduct
}
