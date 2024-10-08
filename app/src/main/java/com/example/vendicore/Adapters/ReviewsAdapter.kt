package com.example.vendicore.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Models.Review
import com.example.vendicore.R

//class ReviewsAdapter(private var reviewsList: List<Review>,) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
//        return ReviewViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
//        val review = reviewsList[position]
//        holder.vendorName.text = review.vendorName
//        holder.ratingBar.rating = review.rating
//        holder.comment.text = review.comment
//        holder.date.text = review.date
//
//        holder.itemView.setOnClickListener {
//            onItemClick(review) // Call the listener with the clicked review
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return reviewsList.size
//    }
//
//    fun updateList(newList: List<Review>) {
//        reviewsList = newList
//        notifyDataSetChanged()
//    }
//
//    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val vendorName: TextView = itemView.findViewById(R.id.reviewVendorName)
//        val ratingBar: RatingBar = itemView.findViewById(R.id.reviewRatingBar)
//        val comment: TextView = itemView.findViewById(R.id.reviewComment)
//        val date: TextView = itemView.findViewById(R.id.reviewDate)
//    }
//}

class ReviewsAdapter(
    private var reviewsList: List<Review>,
    private val onItemClick: (Review) -> Unit // Add this parameter for the click listener
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
        holder.vendorName.text = review.vendorName
        holder.ratingBar.rating = review.rating
        holder.comment.text = review.comment
        holder.date.text = review.date
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    fun updateList(newList: List<Review>) {
        reviewsList = newList
        notifyDataSetChanged()
    }
}

