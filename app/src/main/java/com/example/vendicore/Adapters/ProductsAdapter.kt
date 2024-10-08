package com.example.vendicore.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Models.CartItem
import com.example.vendicore.Activity.ProductDetailsActivity
import com.example.vendicore.Models.Product
import com.example.vendicore.R
import com.example.vendicore.ViewModels.CartViewModel
import com.example.vendicore.ui.HomeFragment

class ProductAdapter(private val products: List<Product>, private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productRating: TextView = itemView.findViewById(R.id.productRating)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val addToCartButton: ImageButton = itemView.findViewById(R.id.addToCartButton)

        init {
            // Set click listener for the product item
            itemView.setOnClickListener {
                // Get the position of the clicked item
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Navigate to ProductDetailsActivity
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
         val product = products[position]
        holder.productImage.setImageResource(product.imageResId)
        holder.productName.text = product.name
        holder.productRating.text = "${product.rating} ★"
        holder.productPrice.text = "Rs. ${product.price}"

        // Optional: Set up the add to cart button logic here if needed
        holder.addToCartButton.setOnClickListener {
            // Implement add to cart logic here
        }
    }

    override fun getItemCount() = products.size
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productRating: TextView = itemView.findViewById(R.id.productRating)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)

        fun bind(product: Product) {
            productName.text = product.name
            productRating.text = product.rating.toString()
            productPrice.text = "$${product.price}"
            productImage.setImageResource(product.imageResId)
        }
    }
}
