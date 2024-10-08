package com.example.vendicore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vendicore.Adapters.CartAdapter
import com.example.vendicore.Models.CartItem
import com.example.vendicore.ViewModels.CartViewModel
import com.example.vendicore.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        cartAdapter = CartAdapter(cartViewModel.cartItems.value ?: emptyList(), cartViewModel)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cartRecyclerView.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner, Observer {
            cartAdapter.updateCartItems(it)
            updateTotalPrice(it)
        })

        binding.buttonPurchase.setOnClickListener {
            val paymentDialog = PaymentDetailsDialogFragment()
            paymentDialog.show(parentFragmentManager, "PaymentDetailsDialogFragment")
        }
    }

    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.productPrice * it.quantity }
        binding.totalItemPrice.text = "Total: $$totalPrice"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}