package com.fortoszone.moviedb.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.fortoszone.moviedb.model.Movie
import com.fortoszone.moviedb.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    fun loadPopularMovie(context: Context, callback: (List<Movie>) -> Unit) {
        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getPopular()
            .enqueue(object : Callback<PopularMovieResponse> {
                override fun onResponse(
                    call: Call<PopularMovieResponse>,
                    response: Response<PopularMovieResponse>
                ) {
                    return callback(response.body()!!.movies)
                }

                override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                    Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    fun loadTopRatedMovies(context: Context, callback: (List<Movie>) -> Unit) {
        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getTopRated()
            .enqueue(object : Callback<TopRatedMovieResponse> {
                override fun onResponse(
                    call: Call<TopRatedMovieResponse>,
                    response: Response<TopRatedMovieResponse>
                ) {
                    return callback(response.body()!!.movies)
                }

                override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                    Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG)
                        .show()

                }
            })
    }

    fun loadNowPlayingMovies(context: Context, callback: (List<Movie>) -> Unit) {
        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getNowPlaying()
            .enqueue(object : Callback<NowPlayingMovieResponse> {
                override fun onResponse(
                    call: Call<NowPlayingMovieResponse>,
                    response: Response<NowPlayingMovieResponse>
                ) {
                    return callback(response.body()!!.movies)
                }

                override fun onFailure(call: Call<NowPlayingMovieResponse>, t: Throwable) {
                    Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}