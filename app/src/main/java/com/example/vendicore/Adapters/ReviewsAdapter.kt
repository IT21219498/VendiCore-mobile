package com.example.vendicore.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Models.VendorReview  // Use VendorReview instead of Review
import com.example.vendicore.R
import java.text.SimpleDateFormat
import java.util.Locale

class ReviewsAdapter(
    private var reviewsList: List<VendorReview>, // Change type to VendorReview
    private val onItemClick: (VendorReview) -> Unit // Use VendorReview here as well
) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vendorName: TextView = itemView.findViewById(R.id.reviewVendorName)
        val ratingBar: RatingBar = itemView.findViewById(R.id.reviewRatingBar)
        val comment: TextView = itemView.findViewById(R.id.reviewComment)
        val date: TextView = itemView.findViewById(R.id.reviewDate)

        init {
            // Set the click listener for the itemView
            itemView.setOnClickListener {
                // Call the onItemClick function with the current review
                onItemClick(reviewsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewsList[position]
        holder.vendorName.text = review.vendorId // Display VendorId or relevant field
        holder.ratingBar.rating = review.rating
        holder.comment.text = review.comment

        // Format and display the date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val date = dateFormat.parse(review.createdAt)
        val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)

        holder.date.text = formattedDate // Set the formatted date
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    fun updateList(newList: List<VendorReview>) {
        reviewsList = newList
        notifyDataSetChanged()
    }
}
