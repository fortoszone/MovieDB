package com.fortoszone.moviedb.model.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieService {
    /*private const val BASE_URL = "https://api.themoviedb.org/"
    private var movieService: MovieService? = null
    fun getInstance(): MovieService {
        if (movieService == null) {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

            movieService = retrofit.create(MovieService::class.java)
        }
        return movieService!!
    }*/

    private const val BASE_URL = "https://api.themoviedb.org/"
    private var retrofit: Retrofit? = null

    fun getInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}