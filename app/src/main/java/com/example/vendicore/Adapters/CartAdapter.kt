package com.example.vendicore.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Models.CartItem
import com.example.vendicore.R
import com.example.vendicore.ViewModels.CartViewModel
import com.squareup.picasso.Picasso

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val cartViewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartProductImage: ImageView = itemView.findViewById(R.id.cartProductImage)
        val cartProductName: TextView = itemView.findViewById(R.id.cartProductName)
        val cartProductVendor: TextView = itemView.findViewById(R.id.cartProductVendor)
        val cartProductPrice: TextView = itemView.findViewById(R.id.cartProductPrice)
        val cartProductTotalPrice: TextView = itemView.findViewById(R.id.cartProductTotalPrice)
        val buttonRemove: ImageView = itemView.findViewById(R.id.buttonRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        Picasso.get().load(cartItem.imageUrl).into(holder.cartProductImage)
        holder.cartProductName.text = cartItem.productName
        holder.cartProductVendor.text = "Vendor: ${cartItem.vendorName}"
        holder.cartProductPrice.text = "Price: ${cartItem.productPrice}"
        holder.cartProductTotalPrice.text = "Total: ${cartItem.productPrice * cartItem.quantity}"

        holder.buttonRemove.setOnClickListener {
            cartViewModel.removeItem(cartItem)
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun updateCartItems(newCartItems: List<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }
}