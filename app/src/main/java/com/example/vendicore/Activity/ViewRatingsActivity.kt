package com.example.vendicore.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Adapters.ReviewsAdapter
import com.example.vendicore.Models.VendorReview
import com.example.vendicore.R
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewRatingsActivity : AppCompatActivity() {

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var filterSpinner: Spinner
    private lateinit var vendorSpinner: Spinner
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var reviewsList: MutableList<VendorReview>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_ratings)

        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)
        filterSpinner = findViewById(R.id.filterSpinner)
        vendorSpinner = findViewById(R.id.vendorSpinner)

        // Setup RecyclerView
        reviewsList = mutableListOf()
        reviewsAdapter = ReviewsAdapter(reviewsList) { review -> showEditCommentDialog(review) }
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        reviewsRecyclerView.adapter = reviewsAdapter

        // Load reviews from backend
        loadReviews()

        // Populate the filter dropdown
        val filterOptions = listOf("All Ratings", "5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star")
        val filterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = filterAdapter

        // Set rating filter logic
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                val selectedRating = position  // Position corresponds to star rating (0 for All Ratings)
                filterReviewsByRating(selectedRating)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        //set vendor filter logic
        vendorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                filterReviews()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun loadReviews() {
        val apiService = RetrofitClient.instance

        apiService.getReviews().enqueue(object : Callback<List<VendorReview>> {
            override fun onResponse(call: Call<List<VendorReview>>, response: Response<List<VendorReview>>) {
                if (response.isSuccessful) {
                    reviewsList.clear()
                    response.body()?.let { reviewsList.addAll(it) }
                    reviewsAdapter.notifyDataSetChanged()
                    Log.d("ViewRatingsActivity", "Reviews loaded successfully: $reviewsList")
                } else {
                    Log.e("ViewRatingsActivity", "Failed to load reviews: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<VendorReview>>, t: Throwable) {
                Log.e("ViewRatingsActivity", "API call failed: ${t.message}")
                Toast.makeText(this@ViewRatingsActivity, "Failed to load reviews", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showEditCommentDialog(review: VendorReview) {
        // Inflate the dialog layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_comment, null)
        val editComment = dialogView.findViewById<EditText>(R.id.editTextComment)
        val saveButton = dialogView.findViewById<Button>(R.id.CbuttonUpdate)

        // Set the current comment in the EditText
        editComment.setText(review.comment)

        // Create and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Comment")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        saveButton.setOnClickListener {
            val updatedComment = editComment.text.toString()
            review.comment = updatedComment  // Update the review object

            // Call backend to update the review
            updateReview(review)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateReview(review: VendorReview) {
        val apiService = RetrofitClient.instance

        // Extracting the string representation of the ObjectId (from review.id)
        val objectId = review.id?.toString() ?: return Toast.makeText(this, "Review ID is missing", Toast.LENGTH_SHORT).show()

        // Use only the string ObjectId, not the whole Id object
        apiService.updateReview(objectId, review.comment).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ViewRatingsActivity, "Review updated successfully", Toast.LENGTH_SHORT).show()
                    reviewsAdapter.notifyDataSetChanged()
                } else {
                    Log.e("ViewRatingsActivity", "Failed to update review: ${response.errorBody()?.string()}")
                    Toast.makeText(this@ViewRatingsActivity, "Failed to update Comment", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ViewRatingsActivity", "API call failed: ${t.message}")
                Toast.makeText(this@ViewRatingsActivity, "Failed to update Comment", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterReviews() {
        val selectedRatingPosition = filterSpinner.selectedItemPosition
        val selectedVendor = vendorSpinner.selectedItem.toString()

        val filteredReviews = reviewsList.filter { review ->
            val ratingMatches = if (selectedRatingPosition == 0) true else review.rating.toInt() == (6 - selectedRatingPosition)
            val vendorMatches = selectedVendor == "All Vendors" || review.vendorId == selectedVendor
            ratingMatches && vendorMatches
        }

        reviewsAdapter.updateList(filteredReviews)
    }

    private fun filterReviewsByRating(selectedRatingPosition: Int) {
        val filteredReviews = when (selectedRatingPosition) {
            0 -> reviewsList  // All Ratings
            1 -> reviewsList.filter { it.rating.toInt() == 5 }  // 5 Stars
            2 -> reviewsList.filter { it.rating.toInt() == 4 }  // 4 Stars
            3 -> reviewsList.filter { it.rating.toInt() == 3 }  // 3 Stars
            4 -> reviewsList.filter { it.rating.toInt() == 2 }  // 2 Stars
            5 -> reviewsList.filter { it.rating.toInt() == 1 }  // 1 Star
            else -> reviewsList  // In case something goes wrong, show all reviews
        }

        reviewsAdapter.updateList(filteredReviews)
    }
}
