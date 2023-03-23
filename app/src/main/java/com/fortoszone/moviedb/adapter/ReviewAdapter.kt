package com.fortoszone.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ReviewRowBinding
import com.fortoszone.moviedb.model.local.entity.Review

class ReviewAdapter(private val reviews: List<Review>) :
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
            binding.tvMovieRating.text = review.authorDetail.rating
            binding.tvMovieReview.text = review.content
            binding.tvMovieReviewDate.text = review.createdAt
        }
    }
}