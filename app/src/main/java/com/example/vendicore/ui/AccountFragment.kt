//package com.example.vendicore.ui
//
//import com.example.vendicore.databinding.FragmentAccountBinding
//import android.content.Context
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.vendicore.R
//import com.example.vendicore.network.CustomerUpdateDTO
//import com.example.vendicore.network.RetrofitClient
//import com.example.vendicore.network.UpdateApiResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import android.content.Intent
//import com.example.vendicore.Activity.LoginActivity
//import okhttp3.*
//
//
//
//
//class AccountFragment : Fragment() {
//    private var _binding: FragmentAccountBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var nameEditText: EditText
//    private lateinit var emailEditText: EditText
//    private lateinit var phoneEditText: EditText
//    private lateinit var addressEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var updateButton: Button
//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var deactivateButton: Button
//    private val client = OkHttpClient()
//
//
//
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View {
////        _binding = FragmentAccountBinding.inflate(inflater, container, false)
////        return binding.root
////    }
////
////    override fun onDestroyView() {
////        super.onDestroyView()
////        _binding = null
////    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_account, container, false)
//
//        // Initialize views
//        nameEditText = view.findViewById(R.id.account_name)
//        emailEditText = view.findViewById(R.id.account_email)
//        phoneEditText = view.findViewById(R.id.account_phone)
//        addressEditText = view.findViewById(R.id.account_address)
//        passwordEditText = view.findViewById(R.id.account_password)
//        updateButton = view.findViewById(R.id.btn_update_account)
//        val signOutButton = view.findViewById<Button>(R.id.btn_logout) // Initialize the Sign Out button
//
//        // Initialize SharedPreferences
//        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//
//        // Load the user data into the input fields
//        loadUserData()
//
//        // Set up Sign Out button click listener
//        signOutButton.setOnClickListener {
//            signOutUser()
//        }
//
//
//
//
//        // Set up update button click listener
//        updateButton.setOnClickListener {
//            updateUserData()
//        }
//
//        return view
//    }
//    private fun signOutUser() {
//        // Clear shared preferences
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()
//
//        // Redirect to login page
//        val intent = Intent(requireContext(), LoginActivity::class.java)
//        startActivity(intent)
//
//        // Close the current activity or fragment so that the user can't go back to the account page
//        requireActivity().finish()
//    }
//
//    private fun loadUserData() {
//        // Retrieve data from SharedPreferences
//        val name = sharedPreferences.getString("fullName", "")
//        val email = sharedPreferences.getString("email", "")
//        val phone = sharedPreferences.getString("phoneNumber", "")
//        val address = sharedPreferences.getString("address", "")
////        val password = sharedPreferences.getString("password", "") // If storing hashed password, remove from here
//
//        // Set the data to the corresponding input fields
//        nameEditText.setText(name)
//        emailEditText.setText(email)
//        phoneEditText.setText(phone)
//        addressEditText.setText(address)
////        passwordEditText.setText(password) // Don't store plain password in shared preferences
//    }
//
//    private fun updateUserData() {
//        // Retrieve values from input fields
//        val updatedName = nameEditText.text.toString()
//        val updatedPhone = phoneEditText.text.toString()
//        val updatedAddress = addressEditText.text.toString()
//
//        // Create a CustomerUpdateDTO object
//        val updateDTO = CustomerUpdateDTO(
//            fullName = updatedName.takeIf { it.isNotEmpty() },
//            phoneNumber = updatedPhone.takeIf { it.isNotEmpty() },
//            address = updatedAddress.takeIf { it.isNotEmpty() }
//        )
//
//
//        // Retrieve the user ID from SharedPreferences
//        val userId = sharedPreferences.getString("userId", "")
//
//        if (userId.isNullOrEmpty()) {
//            Toast.makeText(
//                requireContext(),
//                "User ID not found. Please log in again.",
//                Toast.LENGTH_SHORT
//            ).show()
//            return
//        }
//
//        // Make the PUT request to update the user data
//        RetrofitClient.instance.modifyCustomer(userId, updateDTO)
//            .enqueue(object : Callback<UpdateApiResponse> {
//                override fun onResponse(
//                    call: Call<UpdateApiResponse>,
//                    response: Response<UpdateApiResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        response.body()?.let {
//                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//
//                            // Optionally save updated data in SharedPreferences
//                            val editor = sharedPreferences.edit()
//                            editor.putString("fullName", updatedName)
//                            editor.putString("phoneNumber", updatedPhone)
//                            editor.putString("address", updatedAddress)
//                            editor.apply()
//                        }
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "Failed to update account",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<UpdateApiResponse>, t: Throwable) {
//                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            })
//
//    }}
package com.example.vendicore.ui

import com.example.vendicore.databinding.FragmentAccountBinding
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vendicore.R
import com.example.vendicore.network.CustomerUpdateDTO
import com.example.vendicore.network.RetrofitClient
import com.example.vendicore.network.UpdateApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import com.example.vendicore.Activity.LoginActivity
import okhttp3.*

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var deactivateButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Initialize views
        nameEditText = view.findViewById(R.id.account_name)
        emailEditText = view.findViewById(R.id.account_email)
        phoneEditText = view.findViewById(R.id.account_phone)
        addressEditText = view.findViewById(R.id.account_address)
        passwordEditText = view.findViewById(R.id.account_password)
        updateButton = view.findViewById(R.id.btn_update_account)
        deactivateButton = view.findViewById(R.id.btn_deactivate_account) // Deactivate button initialization
        val signOutButton = view.findViewById<Button>(R.id.btn_logout) // Initialize the Sign Out button

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Load the user data into the input fields
        loadUserData()

        // Set up Sign Out button click listener
        signOutButton.setOnClickListener {
            signOutUser()
        }

        // Set up update button click listener
        updateButton.setOnClickListener {
            updateUserData()
        }

        // Set up deactivate button click listener
        deactivateButton.setOnClickListener {
            deactivateAccount()
        }

        return view
    }

    private fun signOutUser() {
        // Clear shared preferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect to login page
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)

        // Close the current activity or fragment so that the user can't go back to the account page
        requireActivity().finish()
    }

    private fun loadUserData() {
        // Retrieve data from SharedPreferences
        val name = sharedPreferences.getString("fullName", "")
        val email = sharedPreferences.getString("email", "")
        val phone = sharedPreferences.getString("phoneNumber", "")
        val address = sharedPreferences.getString("address", "")

        // Set the data to the corresponding input fields
        nameEditText.setText(name)
        emailEditText.setText(email)
        phoneEditText.setText(phone)
        addressEditText.setText(address)
    }

    private fun updateUserData() {
        // Retrieve values from input fields
        val updatedName = nameEditText.text.toString()
        val updatedPhone = phoneEditText.text.toString()
        val updatedAddress = addressEditText.text.toString()
        val updatedPassword = passwordEditText.text.toString() // Retrieve the new password


        val updateDTO = CustomerUpdateDTO(
            fullName = updatedName.takeIf { it.isNotEmpty() },
            phoneNumber = updatedPhone.takeIf { it.isNotEmpty() },
            address = updatedAddress.takeIf { it.isNotEmpty() },
            password = updatedPassword.takeIf { it.isNotEmpty() } // This line
        )


        // Retrieve the user ID from SharedPreferences
        val userId = sharedPreferences.getString("userId", "")

        if (userId.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                "User ID not found. Please log in again.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Make the PUT request to update the user data
        RetrofitClient.instance.modifyCustomer(userId, updateDTO)
            .enqueue(object : Callback<UpdateApiResponse> {
                override fun onResponse(call: Call<UpdateApiResponse>, response: Response<UpdateApiResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                            // Optionally save updated data in SharedPreferences
                            val editor = sharedPreferences.edit()
                            editor.putString("fullName", updatedName)
                            editor.putString("phoneNumber", updatedPhone)
                            editor.putString("address", updatedAddress)
                            editor.apply()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to update account",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UpdateApiResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun deactivateAccount() {
        // Retrieve the user ID from SharedPreferences
        val userId = sharedPreferences.getString("userId", "")

        if (userId.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                "User ID not found. Please log in again.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Make the POST request to deactivate the user account
        RetrofitClient.instance.deactivateCustomer(userId).enqueue(object : Callback<UpdateApiResponse> {
            override fun onResponse(call: Call<UpdateApiResponse>, response: Response<UpdateApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                        // Sign out user after successful deactivation
                        signOutUser()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to deactivate account", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateApiResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
