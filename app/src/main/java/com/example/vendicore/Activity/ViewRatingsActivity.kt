package com.example.vendicore.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Adapters.ReviewsAdapter
import com.example.vendicore.Models.Review
import com.example.vendicore.R

class ViewRatingsActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_view_ratings)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var filterSpinner: Spinner
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var reviewsList: MutableList<Review>  // Model to store reviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_ratings)

        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)
        filterSpinner = findViewById(R.id.filterSpinner)

        // Setup RecyclerView
        reviewsList = mutableListOf()
//        reviewsAdapter = ReviewsAdapter(reviewsList)
        reviewsAdapter = ReviewsAdapter(reviewsList) { review ->
            showEditCommentDialog(review) // Show dialog when item is clicked
        }
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        reviewsRecyclerView.adapter = reviewsAdapter

        // Load static reviews
        loadStaticReviews()

        // Populate the filter dropdown with options like "All Ratings", "5 Stars", "4 Stars", etc.
        val filterOptions = listOf("All Ratings", "5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star")
        val filterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = filterAdapter

        // Set filter logic
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedRating = position // Position corresponds to star rating (0 for All Ratings)
                filterReviewsByRating(selectedRating)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun showEditCommentDialog(review: Review) {
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
            // Update the review object (make sure your Review model is mutable or use another way to update the comment)
            review.comment = updatedComment

            // Notify the adapter about the change
            reviewsAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        dialog.show()
    }

    // Function to load static reviews
    private fun loadStaticReviews() {
        // Static list of reviews
        val staticReviews = listOf(
            Review("Arpico Holdings", 5f, "Great service!", "12th October 2024"),
            Review("Leema creations", 4f, "Good quality products.", "11th October 2024"),
//            ReviewModel("Vendor 3", 3f, "Average experience.", "10th October 2024"),
//            ReviewModel("Vendor 1", 2f, "Could be better.", "9th October 2024"),
//            ReviewModel("Vendor 2", 5f, "Excellent delivery!", "8th October 2024")
        )

        reviewsList.clear()
        reviewsList.addAll(staticReviews)

        // Notify adapter of the change
        reviewsAdapter.notifyDataSetChanged()
    }

    // Function to filter reviews based on selected rating
    private fun filterReviewsByRating(selectedRating: Int) {
        val filteredReviews = if (selectedRating == 0) {
            reviewsList  // All Ratings
        } else {
            reviewsList.filter { it.rating.toInt() == selectedRating }
        }

        reviewsAdapter.updateList(filteredReviews)
    }

}