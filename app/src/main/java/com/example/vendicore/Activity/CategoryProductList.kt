package com.example.vendicore.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendicore.Adapters.ProductAdapter
import com.example.vendicore.ViewModels.ProductViewModel
import com.example.vendicore.databinding.ActivityCategoryProductListBinding

class CategoryProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryProductListBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("CATEGORY_NAME")

        // Set up the action bar with back navigation
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = categoryName
        }

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productAdapter = ProductAdapter(emptyList(), this, null)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = productAdapter

        val categoryId = intent.getStringExtra("CATEGORY_ID")
        if (categoryId != null) {
            productViewModel.fetchProductsByCategory(categoryId)
        }

        productViewModel.products.observe(this, Observer { products ->
            productAdapter.updateProducts(products)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}