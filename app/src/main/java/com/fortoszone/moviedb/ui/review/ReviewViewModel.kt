package com.fortoszone.moviedb.ui.review

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.fortoszone.moviedb.model.Movie
import com.fortoszone.moviedb.model.Review
import com.fortoszone.moviedb.ui.detail.DetailActivity
import com.fortoszone.moviedb.utils.ApiInterface
import com.fortoszone.moviedb.utils.ApiService
import com.fortoszone.moviedb.utils.ReviewMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel : ViewModel() {
    fun loadMovieReview(context: Context, intent: Intent, callback: (List<Review>) -> Unit) {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DetailActivity.EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(DetailActivity.EXTRA_DETAILS) as Movie
        }

        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getReview(movie!!.id)
            .enqueue(object : Callback<ReviewMovieResponse> {
                override fun onResponse(
                    call: Call<ReviewMovieResponse>,
                    response: Response<ReviewMovieResponse>
                ) {
                    return callback(response.body()!!.reviews)
                }

                override fun onFailure(call: Call<ReviewMovieResponse>, t: Throwable) {
                    Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}