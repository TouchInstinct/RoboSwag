package ru.touchin.roboswag.cart_utils.update_manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.touchin.roboswag.cart_utils.models.CartModel
import ru.touchin.roboswag.cart_utils.models.ProductModel
import ru.touchin.roboswag.cart_utils.models.PromocodeModel
import ru.touchin.roboswag.cart_utils.repositories.IRemoteCartRepository
import ru.touchin.roboswag.cart_utils.repositories.LocalCartRepository
import ru.touchin.roboswag.cart_utils.requests_qeue.Request
import ru.touchin.roboswag.cart_utils.requests_qeue.RequestsQueue

/**
 * Combines local and remote cart update actions
 */
open class CartUpdateManager<TCart : CartModel<TProduct>, TProduct : ProductModel>(
        private val localCartRepository: LocalCartRepository<TCart, TProduct>,
        private val remoteCartRepository: IRemoteCartRepository<TCart, TProduct>,
        private val maxRequestAttemptsCount: Int = MAX_REQUEST_CART_ATTEMPTS_COUNT,
        private val errorHandler: (Throwable) -> Unit = {},
) {

    companion object {
        private const val MAX_REQUEST_CART_ATTEMPTS_COUNT = 3
    }

    private val requestsQueue = RequestsQueue<Request<TCart>>()

    @Volatile
    var lastRemoteCart: TCart? = null
        private set

    fun initCartRequestsQueue(
            coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    ) {
        requestsQueue.initRequestsExecution(coroutineScope) { request ->
            runCatching {
                lastRemoteCart = request.invoke()
                if (!requestsQueue.hasPendingRequests()) updateLocalCartWithRemote()
            }.onFailure { error ->
                errorHandler.invoke(error)
                requestsQueue.clearQueue()
                tryToGetRemoteCartAgain()
            }
        }
    }

    open fun addProduct(product: TProduct, restoreDeleted: Boolean = false) {
        with(localCartRepository) {
            if (restoreDeleted) restoreDeletedProduct(product.id) else addProduct(product)
        }

        requestsQueue.addToQueue {
            remoteCartRepository.addProduct(product)
        }
    }

    open fun removeProduct(id: Int, markDeleted: Boolean = false) {
        with(localCartRepository) {
            if (markDeleted) markProductDeleted(id) else removeProduct(id)
        }

        requestsQueue.addToQueue {
            remoteCartRepository.removeProduct(id)
        }
    }

    open fun editProductCount(id: Int, count: Int) {
        localCartRepository.editProductCount(id, count)

        requestsQueue.addToQueue {
            remoteCartRepository.editProductCount(id, count)
        }
    }

    open fun completelyDeleteProduct(id: Int) {
        localCartRepository.removeProduct(id)
    }

    open fun applyPromocode(promocode: PromocodeModel) {
        localCartRepository.applyPromocode(promocode)
    }

    open fun removePromocode(code: String) {
        localCartRepository.removePromocode(code)
    }

    private suspend fun tryToGetRemoteCartAgain() {
        repeat(maxRequestAttemptsCount) {
            runCatching {
                lastRemoteCart = remoteCartRepository.getCart()
                updateLocalCartWithRemote()
                return
            }
        }
    }

    private fun updateLocalCartWithRemote() {
        val remoteCart = lastRemoteCart ?: return
        val remoteProducts = remoteCart.products
        val localProducts = localCartRepository.currentCart.value.products

        val newProductsFromRemoteCart = remoteProducts.filter { remoteProduct ->
            localProducts.none { it.id == remoteProduct.id }
        }

        val mergedProducts = localProducts.mapNotNull { localProduct ->
            val sameRemoteProduct = remoteProducts.find { it.id == localProduct.id }

            when {
                sameRemoteProduct != null -> sameRemoteProduct
                localProduct.isDeleted -> localProduct
                else -> null
            }
        }

        val mergedCart = remoteCart.copyWith<TCart>(products = mergedProducts + newProductsFromRemoteCart)
        localCartRepository.updateCart(mergedCart)
    }

}
