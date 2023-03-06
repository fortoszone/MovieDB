package com.fortoszone.moviedb.utils

import com.fortoszone.moviedb.model.Movie
import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("results")
    val movies : List<Movie>
)
