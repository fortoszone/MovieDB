package com.fortoszone.moviedb.model.remote.response

import com.fortoszone.moviedb.model.local.entity.Review
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("results")
    val reviews: List<Review>
)
