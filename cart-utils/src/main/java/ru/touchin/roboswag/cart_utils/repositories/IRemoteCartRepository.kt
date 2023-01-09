package ru.touchin.roboswag.cart_utils.repositories

import ru.touchin.roboswag.cart_utils.models.CartModel
import ru.touchin.roboswag.cart_utils.models.ProductModel

/**
 * Interface for server-side cart repository where each request should return updated [CartModel]
 */
interface IRemoteCartRepository<TCart : CartModel<TProduct>, TProduct : ProductModel> {

    suspend fun getCart(): TCart

    suspend fun addProduct(product: TProduct): TCart

    suspend fun removeProduct(id: Int): TCart

    suspend fun editProductCount(id: Int, count: Int): TCart

}
