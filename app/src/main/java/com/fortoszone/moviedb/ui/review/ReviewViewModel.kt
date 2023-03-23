package com.fortoszone.moviedb.ui.review

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.model.local.entity.Review

class ReviewViewModel(private val repository: MovieRepository) : ViewModel() {
    fun loadMovieReview(intent: Intent, callback: (List<Review>) -> Unit) {
        repository.getMovieReviews(intent, callback)
    }
}