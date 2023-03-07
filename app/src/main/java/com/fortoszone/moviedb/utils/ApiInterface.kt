package com.fortoszone.moviedb.utils

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/3/movie/popular?api_key=078e8fe79377bcac312b276a6f7ed8fa")
    fun getPopular(): Call<PopularMovieResponse>

    @GET("/3/movie/top_rated?api_key=078e8fe79377bcac312b276a6f7ed8fa")
    fun getTopRated(): Call<TopRatedMovieResponse>

    @GET("/3/movie/now_playing?api_key=078e8fe79377bcac312b276a6f7ed8fa")
    fun getNowPlaying(): Call<NowPlayingMovieResponse>

    @GET("/3/movie/238/reviews?api_key=078e8fe79377bcac312b276a6f7ed8fa")
    fun getReview(): Call<ReviewMovieResponse>

}