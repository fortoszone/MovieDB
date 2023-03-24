package com.fortoszone.moviedb.model.remote

import android.content.Intent
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.model.local.entity.Review
import com.fortoszone.moviedb.model.remote.response.MovieResponse
import com.fortoszone.moviedb.model.remote.response.ReviewMovieResponse
import com.fortoszone.moviedb.model.remote.retrofit.MovieInterface
import com.fortoszone.moviedb.model.remote.retrofit.MovieService
import com.fortoszone.moviedb.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    val errorMsg = MutableLiveData<String>()

    fun getPopularMovie(callback: (List<Movie>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getPopular()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    return callback(response.body()!!.movies)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    errorMsg.postValue(t.message)
                }
            })
    }

    fun getTopRatedMovie(callback: (List<Movie>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getTopRated().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }

        })

    }

    fun getNowPlayingMovie(callback: (List<Movie>) -> Unit) {
        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getNowPlaying().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }

        })
    }

    fun getMovieReview(intent: Intent, callback: (List<Review>) -> Unit) {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DetailActivity.EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(DetailActivity.EXTRA_DETAILS) as Movie
        }

        val apiService = MovieService.getInstance().create(MovieInterface::class.java)
        apiService.getReview(movie!!.id).enqueue(object : Callback<ReviewMovieResponse> {
            override fun onResponse(
                call: Call<ReviewMovieResponse>,
                response: Response<ReviewMovieResponse>
            ) {
                return callback(response.body()!!.reviews)
            }

            override fun onFailure(call: Call<ReviewMovieResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource()
        }
    }
}