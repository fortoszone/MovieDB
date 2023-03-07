package com.fortoszone.moviedb.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.model.Review
import com.fortoszone.moviedb.utils.ApiInterface
import com.fortoszone.moviedb.utils.ApiService
import com.fortoszone.moviedb.utils.ReviewMovieResponse
import com.fortoszone.moviedb.viewmodel.ReviewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReviewFragment : Fragment() {
    private lateinit var rvMovieReview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_review, container, false)
        rvMovieReview = view.findViewById(R.id.rv_movie_review) as RecyclerView
        rvMovieReview.setHasFixedSize(true)

        loadMovieReview { review: List<Review> ->
            rvMovieReview.adapter = ReviewAdapter(review)
        }

        rvMovieReview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    private fun loadMovieReview(callback: (List<Review>) -> Unit) {
        val apiService = ApiService.getInstance().create(ApiInterface::class.java)
        apiService.getReview()
            .enqueue(object : Callback<ReviewMovieResponse> {
                override fun onResponse(
                    call: Call<ReviewMovieResponse>,
                    response: Response<ReviewMovieResponse>
                ) {
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()

                    return callback(response.body()!!.reviews)
                    //callback(response.body()!!.authorDetails)


                }

                override fun onFailure(call: Call<ReviewMovieResponse>, t: Throwable) {
                    Toast.makeText(context, "An error has occured", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}
