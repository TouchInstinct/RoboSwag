package ru.touchin.roboswag.cart_utils.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.touchin.roboswag.cart_utils.models.CartModel
import ru.touchin.roboswag.cart_utils.models.ProductModel

/**
 * Class that contains StateFlow of current [CartModel] which can be subscribed in ViewModels
 */
class LocalCartRepository<TCart : CartModel<TProduct>, TProduct : ProductModel>(
        initialCart: TCart
) {

    private val _currentCart = MutableStateFlow(initialCart)
    val currentCart = _currentCart.asStateFlow()

    fun updateCart(cart: TCart) {
        _currentCart.value = cart
    }

    fun addProduct(product: TProduct) {
        updateCartProducts {
            add(product)
        }
    }

    fun removeProduct(id: Int) {
        updateCartProducts {
            val product = find { it.id == id }
            remove(product)
        }
    }

    fun editProductCount(id: Int, count: Int) {
        updateCartProducts {
            updateProduct(id) { copyWith(countInCart = count) }
        }
    }

    fun markProductDeleted(id: Int) {
        updateCartProducts {
            updateProduct(id) { copyWith(isDeleted = true) }
        }
    }

    fun restoreDeletedProduct(id: Int) {
        updateCartProducts {
            updateProduct(id) { copyWith(isDeleted = false) }
        }
    }

    private fun updateCartProducts(updateAction: MutableList<TProduct>.() -> Unit) {
        _currentCart.update { cart ->
            cart.copyWith(products = cart.products.toMutableList().apply(updateAction))
        }
    }

    private fun MutableList<TProduct>.updateProduct(id: Int, updateAction: TProduct.() -> TProduct) {
        val index = indexOfFirst { it.id == id }
        if (index >= 0) this[index] = updateAction.invoke(this[index])
    }
}
