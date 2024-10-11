package com.example.vendicore.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Activity.ProductDetailsActivity
import com.example.vendicore.Models.CartItem
import com.example.vendicore.Models.Product
import com.example.vendicore.R
import com.example.vendicore.ViewModels.CartViewModel
import com.example.vendicore.ui.HomeFragment
import com.squareup.picasso.Picasso

class ProductAdapter(
    private var products: List<Product>,
    private val context: Context,
    private val fragment: HomeFragment?
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productRating: TextView = itemView.findViewById(R.id.productRating)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val addToCartButton: ImageView = itemView.findViewById(R.id.addToCartButton)

        init {
            // Set click listener for the product item
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val product = products[position]
                    val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                        putExtra("PRODUCT_IMAGE", product.imageResId)
                        putExtra("PRODUCT_NAME", product.name)
                        putExtra("PRODUCT_RATING", product.rating.toString())
                        putExtra("PRODUCT_PRICE", "Rs. ${product.price}")
                    }
                    context.startActivity(intent)
                }
            }

            addToCartButton.setOnClickListener {
                val product = products[adapterPosition]
                showAddToCartDialog(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        Picasso.get().load(product.imageUrl).into(holder.productImage)
        holder.productName.text = product.name
        holder.productRating.text = "${product.rating} ★"
        holder.productPrice.text = "Rs. ${product.price}"

        holder.addToCartButton.setOnClickListener {
            showAddToCartDialog(product)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(fragment?.requireActivity() as ViewModelStoreOwner).get(CartViewModel::class.java)
    }


    private fun showAddToCartDialog(product: Product) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_add_to_cart, null)

        val dialogProductImage: ImageView = dialogView.findViewById(R.id.dialogProductImage)
        val dialogProductDescription: TextView =
            dialogView.findViewById(R.id.dialogProductDescription)
        val dialogProductVendor: TextView = dialogView.findViewById(R.id.dialogProductVendor)
        val dialogProductPrice: TextView = dialogView.findViewById(R.id.dialogProductPrice)
        val dialogTotalAmount: TextView = dialogView.findViewById(R.id.dialogTotalAmount)
        val buttonDecreaseQuantity: Button = dialogView.findViewById(R.id.buttonDecreaseQuantity)
        val buttonIncreaseQuantity: Button = dialogView.findViewById(R.id.buttonIncreaseQuantity)
        val textQuantity: TextView = dialogView.findViewById(R.id.textQuantity)
        val buttonAddToCart: Button = dialogView.findViewById(R.id.buttonAddToCart)
        val closeButton: ImageView = dialogView.findViewById(R.id.closeButton)

//        dialogProductImage.setImageResource(product.imageResId)
        Picasso.get().load(product.imageUrl).into(dialogProductImage)
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

        buttonAddToCart.setOnClickListener {
            val cartItem = CartItem(
                product.name,
                product.price,
                quantity,
                product.imageUrl,
                "Diamond inc"
            )
            // Assuming you have a viewModel for cart
             cartViewModel.addItem(cartItem)
            dialog.dismiss()
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
