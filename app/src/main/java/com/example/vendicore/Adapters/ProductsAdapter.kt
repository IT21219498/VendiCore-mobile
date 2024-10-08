package com.example.vendicore.Adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Models.CartItem
import com.example.vendicore.Models.Product
import com.example.vendicore.R
import com.example.vendicore.ViewModels.CartViewModel
import com.example.vendicore.ui.HomeFragment

class ProductsAdapter(private val products: List<Product>, private val fragment: HomeFragment) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(fragment.requireActivity()).get(CartViewModel::class.java)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productRating: TextView = itemView.findViewById(R.id.productRating)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val addToCartButton: Button = itemView.findViewById(R.id.addToCartButton)

        fun bind(product: Product) {
            productName.text = product.name
            productRating.text = product.rating.toString()
            productPrice.text = "$${product.price}"
            productImage.setImageResource(product.imageResId)

            addToCartButton.setOnClickListener {
                showAddToCartDialog(product)
            }
        }

        private fun showAddToCartDialog(product: Product) {
            val dialogView = LayoutInflater.from(fragment.requireContext())
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
            dialogProductDescription.text = "This is diamond ring from ${product.vendorName}"
            dialogProductVendor.text = "Vendor: ${product.vendorName}" // Assuming product has a vendorName property
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

            val dialog = AlertDialog.Builder(fragment.requireContext())
                .setView(dialogView)
                .create()

            buttonAddToCart.setOnClickListener {
                val cartItem = CartItem(product.name, product.price, product.imageResId, quantity, product.imageResId, product.vendorName)
                cartViewModel.addItem(cartItem)
                dialog.dismiss()
            }

            closeButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}