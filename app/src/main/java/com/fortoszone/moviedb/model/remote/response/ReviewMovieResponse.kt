package com.fortoszone.moviedb.model.remote.response

import com.fortoszone.moviedb.model.Review
import com.google.gson.annotations.SerializedName

data class ReviewMovieResponse(
    @SerializedName("results")
    val reviews: List<Review>
)
