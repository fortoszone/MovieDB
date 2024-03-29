package com.fortoszone.moviedb.model.remote.response

import com.fortoszone.moviedb.model.local.entity.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie>
)
