package ru.touchin.roboswag.cart_utils.models

open class PromocodeModel(
        val code: String,
        val discount: PromocodeDiscount,
)

abstract class PromocodeDiscount {

    abstract fun applyTo(totalPrice: Int): Int

    class ByValue(private val value: Int) : PromocodeDiscount() {
        override fun applyTo(totalPrice: Int): Int = totalPrice - value
    }

    class ByPercent(private val percent: Int) : PromocodeDiscount() {
        override fun applyTo(totalPrice: Int): Int = totalPrice - (totalPrice * percent / 100)
    }
}
