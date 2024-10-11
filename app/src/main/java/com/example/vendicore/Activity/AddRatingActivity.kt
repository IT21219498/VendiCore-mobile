package com.example.vendicore.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vendicore.Models.Review
import com.example.vendicore.R
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                // Call the function to send data to the backend via Retrofit
                submitRatingToServer(vendor, rating, comment)
            }
        }
    }

    private fun submitRatingToServer(vendor: String, rating: Float, comment: String) {
        val ratingObject = Review(vendor, rating, comment);

        val apiService = RetrofitClient.instance
        apiService.submitRating(ratingObject).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddRatingActivity, "Rating submitted successfully", Toast.LENGTH_SHORT).show()
                    finish()  // Close the activity after submission
                } else {
                    Toast.makeText(this@AddRatingActivity, "Failed to submit rating", Toast.LENGTH_SHORT).show()
                    Log.e("AddRatingActivity", "Failed to submit rating: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AddRatingActivity, "Failed to submit rating", Toast.LENGTH_SHORT).show()
                Log.e("AddRatingActivity", "Network error: ${t.message}")
            }
        })
    }
}
