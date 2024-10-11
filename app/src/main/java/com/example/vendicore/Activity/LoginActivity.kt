package com.example.vendicore.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vendicore.R
import com.example.vendicore.network.LoginRequest
import com.example.vendicore.network.LoginResponse
import com.example.vendicore.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var useremailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        useremailEditText = findViewById(R.id.loginEmail)
        passwordEditText = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton1)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val signUpTextView = findViewById<TextView>(R.id.loginSignUp)
        signUpTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set up login button click listener
        loginButton.setOnClickListener {
            val username = useremailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun performLogin(username: String, password: String) {
        // Create a LoginRequest object
        val loginRequest = LoginRequest(username, password)

        // Make the API call using Retrofit
        RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // Save token, userId, and additional data to SharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.putString("token", it.token)
                        editor.putString("userId", it.customerId)
                        editor.putString("fullName", it.fullName)
                        editor.putString("email", it.email)
                        editor.putString("phoneNumber", it.phoneNumber)
                        editor.putString("address", it.address)
                        editor.apply() // Apply changes to SharedPreferences

                        Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this@LoginActivity, IntroActivity::class.java))
                        finish()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()

                    // Check if errorBody is in JSON format or plain string
                    val errorMessage = if (errorBody != null && errorBody.isNotEmpty()) {
                        try {
                            val jsonObject = JSONObject(errorBody)
                            jsonObject.getString("message") // If the backend sends JSON response
                        } catch (e: Exception) {
                            errorBody // Fallback to the raw error body if parsing fails
                        }
                    } else {
                        "Login failed. Please check your credentials." // Fallback message
                    }

                    when (errorMessage) {
                        "Account pending approval." -> {
                            Toast.makeText(this@LoginActivity, "Your account is pending approval. Please wait for activation.", Toast.LENGTH_LONG).show()
                        }
                        "Account has been deactivated." -> {
                            Toast.makeText(this@LoginActivity, "Your account has been deactivated. Please contact support.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this@LoginActivity, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle error
                Log.e("LoginActivity", "Error logging in", t)
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
