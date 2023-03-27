package com.fortoszone.moviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ReviewRowBinding
import com.fortoszone.moviedb.model.local.entity.Review
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ReviewAdapter(private val context: Context, private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewMovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.review_row, parent, false)
        return ReviewMovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReviewAdapter.ReviewMovieViewHolder,
        position: Int
    ) {
        holder.bind(this.reviews[position])
    }

    override fun getItemCount(): Int {
        return this.reviews.size
    }

    inner class ReviewMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ReviewRowBinding.bind(view)
        fun bind(review: Review) {
            binding.tvReviewAuthor.text = review.authorDetail.author
            review.authorDetail.rating.apply {
                if (this == null) {
                    binding.tvMovieRating.text = context.getString(R.string.no_rating)
                } else
                    binding.tvMovieRating.text = review.authorDetail.rating.toString()
            }
            binding.tvMovieReview.text = review.content
            val timeFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val date = LocalDate.parse(review.createdAt, timeFormat)
            binding.tvMovieReviewDate.text = date.toString()
        }
    }
}