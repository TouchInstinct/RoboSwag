package ru.touchin.roboswag.cart_utils.models

abstract class ProductModel {
    abstract val id: Int
    abstract val countInCart: Int
    abstract val price: Int
    abstract val isAvailable: Boolean
    abstract val isDeleted: Boolean

    open val bonuses: Int? = null

    open val variants: List<ProductModel> = emptyList()
    open val selectedVariantId: Int? = null

    val selectedVariant get() = variants.find { it.id == selectedVariantId }

    abstract fun <TProduct> copyWith(
            countInCart: Int = this.countInCart,
            isDeleted: Boolean = this.isDeleted,
            selectedVariantId: Int? = this.selectedVariantId
    ): TProduct

    @Suppress("UNCHECKED_CAST")
    fun <TProduct> asProduct(): TProduct = this as TProduct
}
