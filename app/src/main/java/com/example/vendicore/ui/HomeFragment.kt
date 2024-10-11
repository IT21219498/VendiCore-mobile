package com.example.vendicore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendicore.Adapters.CategoriesAdapter
import com.example.vendicore.Adapters.ProductAdapter
import com.example.vendicore.ViewModels.CategoryViewModel
import com.example.vendicore.ViewModels.ProductViewModel
import com.example.vendicore.databinding.ActivityHomeBinding

class HomeFragment : Fragment() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        productAdapter = ProductAdapter(emptyList(), requireContext(), this)
        categoryAdapter = CategoriesAdapter(emptyList(), requireContext())
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productsRecyclerView.adapter = productAdapter

        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecyclerView.adapter = categoryAdapter

        productViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            productAdapter.updateProducts(products)
        })

        categoryViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.updateCategories(categories)
        })

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    productViewModel.searchProductsByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.isEmpty()) {
                    productViewModel.fetchProducts()
                }
                return true
            }
        })

        productViewModel.fetchProducts()
        categoryViewModel.fetchCategories()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}