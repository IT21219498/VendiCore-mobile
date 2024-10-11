package com.example.vendicore.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vendicore.Models.CartItem
import com.example.vendicore.Models.Product
import com.example.vendicore.R
import com.example.vendicore.ViewModels.CartViewModel
import com.example.vendicore.ViewModels.ProductViewModel
import com.example.vendicore.databinding.ActivityProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the action bar with back navigation
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Product Details"
        }

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        val productId = intent.getStringExtra("PRODUCT_ID")
        if (productId != null) {
            productViewModel.fetchProductById(productId)
        }

        productViewModel.product.observe(this, { product ->
            displayProductDetails(product)
        })
    }

    private fun displayProductDetails(product: Product) {
        binding.productName.text = product.name
        binding.productPrice.text = "Rs. ${product.price}"
        binding.productDescription.text = product.description
        binding.vendorName.text = "Vendor: ${product.supplierName}"
        Picasso.get().load(product.imageUrl).into(binding.productImage)

        binding.addToCartButton.setOnClickListener {
            showAddToCartDialog(product)
        }
    }

    private fun showAddToCartDialog(product: Product) {
        val dialogView = LayoutInflater.from(this)
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

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        buttonAddToCart.setOnClickListener {
            val cartItem = CartItem(
                productName = product.name,
                productPrice = product.price,
                quantity = quantity,
                imageUrl = product.imageUrl,
                vendorName =  "Diamond inc",
                productId = product.id
            )
            cartViewModel.addItem(cartItem)
            dialog.dismiss()
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}