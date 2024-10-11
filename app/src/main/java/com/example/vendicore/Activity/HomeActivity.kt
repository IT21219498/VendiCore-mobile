//package com.example.vendicore.Activity
//
//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.vendicore.Adapters.CategoriesAdapter
//import com.example.vendicore.Adapters.ProductAdapter
//import com.example.vendicore.R
//import com.example.vendicore.ViewModels.ProductViewModel
//import com.example.vendicore.ui.HomeFragment
//
//class HomeActivity : AppCompatActivity() {
//
//    private lateinit var categoriesRecyclerView: RecyclerView
//    private lateinit var productsRecyclerView: RecyclerView
//    private lateinit var productsAdapter: ProductAdapter
//    private lateinit var categoryAdapter: CategoriesAdapter
//    private val productViewModel: ProductViewModel by viewModels()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
//        productsRecyclerView = findViewById(R.id.productsRecyclerView)
//
//        // Set layout managers
//        categoriesRecyclerView.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        productsRecyclerView.layoutManager =
//            LinearLayoutManager(this)
//
//        productsAdapter = ProductAdapter(emptyList(), this, HomeFragment())
//        productsRecyclerView.adapter = productsAdapter
//
//        categoryAdapter = CategoriesAdapter(emptyList(), this)
//        categoriesRecyclerView.adapter = categoryAdapter
//
//        // Observe products LiveData
//        productViewModel.products.observe(this, Observer { products ->
//            productsAdapter.updateProducts(products)
//        })
//
//        // Observe categories LiveData
//        productViewModel.categories.observe(this, Observer { categories ->
//            categoryAdapter.updateCategories(categories)
//        })
//
//        // Fetch products and categories from API
//        productViewModel.fetchProducts()
//    }
//}