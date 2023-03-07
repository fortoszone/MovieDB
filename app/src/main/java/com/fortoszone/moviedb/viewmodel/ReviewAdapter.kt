package com.fortoszone.moviedb.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ReviewRowBinding
import com.fortoszone.moviedb.model.Review

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
            Glide.with(itemView).load(review.avatarPath)
                .into(binding.imgReviewAuthor)
            binding.tvReviewAuthor.text = review.author
            binding.tvMovieRating.text = review.rating
            binding.tvMovieReview.text = review.content
            binding.tvMovieReviewDate.text = review.createdAt
            binding.tvMovieRating.text = review.rating
        }
    }
}