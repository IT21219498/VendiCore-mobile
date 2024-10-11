package com.example.vendicore.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Activity.CategoryProductListActivity
import com.example.vendicore.Models.Category
import com.example.vendicore.R
import com.squareup.picasso.Picasso

class CategoriesAdapter(
    private var categories: List<Category>,
    private val context: Context
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        // Load image using Picasso or Glide
        val imagePath = "category${position + 1}"
        Picasso.get().load(context.resources.getIdentifier(imagePath, "drawable", context.packageName))
            .placeholder(R.drawable.sample_category_image)
            .into(holder.categoryImage)
        holder.categoryName.text = category.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryProductListActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            intent.putExtra("CATEGORY_NAME", category.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}