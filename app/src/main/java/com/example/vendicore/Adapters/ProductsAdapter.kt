package com.example.vendicore.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Activity.ProductDetailsActivity
import com.example.vendicore.Models.Product
import com.example.vendicore.R
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
            // Implement add to cart logic here
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

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}