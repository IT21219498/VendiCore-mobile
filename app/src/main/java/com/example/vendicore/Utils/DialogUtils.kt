package com.example.vendicore.Utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.vendicore.Models.CartItem
import com.example.vendicore.Models.Product
import com.example.vendicore.R
import com.example.vendicore.ViewModels.CartViewModel

fun showAddToCartDialog(owner: ViewModelStoreOwner, context: Context, product: Product) {
    Log.d("AddToCartDialog", "Showing add to cart dialog for product: ${product.name}")
    val dialogView = LayoutInflater.from(context)
        .inflate(R.layout.dialog_add_to_cart, null)
    val dialogProductImage: ImageView = dialogView.findViewById(R.id.dialogProductImage)
    val dialogProductDescription: TextView = dialogView.findViewById(R.id.dialogProductDescription)
    val dialogProductVendor: TextView = dialogView.findViewById(R.id.dialogProductVendor)
    val dialogProductPrice: TextView = dialogView.findViewById(R.id.dialogProductPrice)
    val dialogTotalAmount: TextView = dialogView.findViewById(R.id.dialogTotalAmount)
    val buttonDecreaseQuantity: Button = dialogView.findViewById(R.id.buttonDecreaseQuantity)
    val buttonIncreaseQuantity: Button = dialogView.findViewById(R.id.buttonIncreaseQuantity)
    val textQuantity: TextView = dialogView.findViewById(R.id.textQuantity)
    val buttonAddToCart: Button = dialogView.findViewById(R.id.buttonAddToCart)
    val closeButton: ImageView = dialogView.findViewById(R.id.closeButton)

    dialogProductImage.setImageResource(product.imageResId)
    dialogProductDescription.text = "This is a diamond ring from ${product.vendorName}"
    dialogProductVendor.text = "Vendor: ${product.vendorName}"
    dialogProductPrice.text = "Price: $${product.price}"
    var quantity = 1
    textQuantity.text = quantity.toString()
    dialogTotalAmount.text = "Total: $${product.price * quantity}"

    buttonDecreaseQuantity.setOnClickListener {
        if (quantity > 1) {
            quantity--
            textQuantity.text = quantity.toString()
            dialogTotalAmount.text = "Total: $${product.price * quantity}"
        }
    }

    buttonIncreaseQuantity.setOnClickListener {
        quantity++
        textQuantity.text = quantity.toString()
        dialogTotalAmount.text = "Total: $${product.price * quantity}"
    }

    val dialog = AlertDialog.Builder(context)
        .setView(dialogView)
        .create()

    val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(owner).get(CartViewModel::class.java)
    }

    buttonAddToCart.setOnClickListener {
        val cartItem = CartItem(
            product.name,
            product.price,
            product.imageResId,
            quantity,
            product.imageResId,
            product.vendorName
        )
        Log.d("AddToCartDialog", "Adding item to cart: $cartItem")
        cartViewModel.addItem(cartItem)
        Log.d("AddToCartDialog", "Cart items: ${cartViewModel.cartItems.value}")
        dialog.dismiss()
    }

    closeButton.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()
}