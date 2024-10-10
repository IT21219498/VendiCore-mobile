package com.example.vendicore.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vendicore.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class AddRatingActivity : AppCompatActivity() {

    private lateinit var vendorSpinner: Spinner
    private lateinit var ratingBar: RatingBar
    private lateinit var commentEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rating)

        vendorSpinner = findViewById(R.id.vendorSpinner)
        ratingBar = findViewById(R.id.vendorRatingBar)
        commentEditText = findViewById(R.id.comment)
        submitButton = findViewById(R.id.submitRatingButton)

        submitButton.setOnClickListener {
            val vendor = vendorSpinner.selectedItem.toString()
            val rating = ratingBar.rating
            val comment = commentEditText.text.toString()

            if (comment.isBlank()) {
                Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show()
            } else {
                // Call the function to send data to the backend
                submitRatingToServer(vendor, rating, comment)
            }
        }
    }

    fun getUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    private fun submitRatingToServer(vendor: String, rating: Float, comment: String) {
        val url = "https://10.0.2.2:7003/api/Rating/newRating"  // Replace with your ASP.NET API endpoint

        // Create the JSON body for the POST request
        val jsonBody = JSONObject()
        jsonBody.put("VendorId", vendor)
        jsonBody.put("Rating", rating)
        jsonBody.put("Comment", comment)

        // Create request body
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaType(),  // Use extension function
            jsonBody.toString()
        )

        val client = getUnsafeOkHttpClient()

        // Create the POST request
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        // Make the HTTP request using OkHttp
//        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@AddRatingActivity, "Failed to submit rating", Toast.LENGTH_SHORT).show()
                    Log.e("AddRatingActivity", "Network error: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful) {
                    runOnUiThread {
                        Toast.makeText(this@AddRatingActivity, "Rating submitted successfully", Toast.LENGTH_SHORT).show()
                        finish()  // Close the activity after submission
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@AddRatingActivity, "Failed to submit rating", Toast.LENGTH_SHORT).show()
                        Log.e("AddRatingActivity", "Failed to submit rating: $responseBody")
                    }
                }
            }
        })
    }
}
