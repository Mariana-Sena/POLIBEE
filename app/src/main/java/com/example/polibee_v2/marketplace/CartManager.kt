package com.example.polibee_v2.marketplace

import androidx.compose.runtime.mutableStateListOf

object CartManager {
    val cartItems = mutableStateListOf<CartItem>()

    fun addProduct(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
    }

    fun removeProduct(product: Product) {
        cartItems.removeAll { it.product.id == product.id }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            if (quantity <= 0) {
                cartItems.remove(existingItem)
            } else {
                existingItem.quantity = quantity
            }
        }
    }

    fun calculateSubtotal(): Double {
        return cartItems.sumOf { it.product.price * it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}