package com.example.vendicore.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Models.Product
import com.example.vendicore.R

class ProductsAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
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
