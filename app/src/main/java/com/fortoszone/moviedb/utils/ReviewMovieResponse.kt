package com.fortoszone.moviedb.utils

import com.fortoszone.moviedb.model.Review
import com.google.gson.annotations.SerializedName

data class ReviewMovieResponse(
    @SerializedName("results")
    val reviews: List<Review>,

    @SerializedName("author_details")
    val authorDetails: List<Review>
)
