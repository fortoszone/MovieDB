package com.fortoszone.moviedb.model.remote.response

import com.fortoszone.moviedb.model.Movie
import com.google.gson.annotations.SerializedName

data class TopRatedMovieResponse (
    @SerializedName("results")
    val movies : List<Movie>
)
