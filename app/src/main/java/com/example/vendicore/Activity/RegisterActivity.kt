package com.example.vendicore.Activity


//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.vendicore.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vendicore.R
import com.example.vendicore.network.RegisterRequest
import com.example.vendicore.network.RegisterResponse
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
//    }

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize the views
        fullNameEditText = findViewById(R.id.registerName)
        emailEditText = findViewById(R.id.registerEmail)
        phoneNumberEditText = findViewById(R.id.registerNumber)
        addressEditText = findViewById(R.id.registerAddress)
        passwordEditText = findViewById(R.id.registerPassword)
        confirmPasswordEditText = findViewById(R.id.registerConfirmPassword)
        signUpButton = findViewById(R.id.signUpButton)

        // Set up the sign-up button click listener
        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val address = addressEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (validateInputs(fullName, email, phoneNumber, address, password, confirmPassword)) {
                performRegistration(fullName, email, phoneNumber, address, password)
            } else {
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateInputs(fullName: String, email: String, phoneNumber: String, address: String, password: String, confirmPassword: String): Boolean {
        return fullName.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() && password == confirmPassword
    }

    private fun performRegistration(fullName: String, email: String, phoneNumber: String, address: String, password: String) {
        val registerRequest = RegisterRequest(fullName, email, phoneNumber, address, password)

        RetrofitClient.instance.registerCustomer(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, response.body()?.message ?: "Registration successful!.Await account activation", Toast.LENGTH_SHORT).show()

                    // Navigate to login screen after registration
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }}