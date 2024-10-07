package com.example.vendicore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Adapters.CategoriesAdapter
import com.example.vendicore.Adapters.ProductsAdapter
import com.example.vendicore.Models.Category
import com.example.vendicore.Models.Product
import com.example.vendicore.R

class HomeFragment : Fragment() {

    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var productsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerViews
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView)
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView)

        // Set layout managers
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        productsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // Set adapters
        val categoriesAdapter = CategoriesAdapter(getCategories())
        categoriesRecyclerView.adapter = categoriesAdapter

        val productsAdapter = ProductsAdapter(getProducts())
        productsRecyclerView.adapter = productsAdapter
    }

    // Mock data for categories
    private fun getCategories(): List<Category> {
        return listOf(
            Category("Sale"),
            Category("Celebration"),
            Category("Arrivals"),
            Category("Bestsellers")
        )
    }

    // Mock data for products
    private fun getProducts(): List<Product> {
        return listOf(
            Product("Diamond Ring", 4.3, 2500.0, R.drawable.diamond_ring),
            Product("Gold Bracelet", 5.0, 3000.0, R.drawable.gold_bracelet)
        )
    }
}
