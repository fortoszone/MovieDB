package com.fortoszone.moviedb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.databinding.ActivityMainBinding
import com.fortoszone.moviedb.model.Movie
import com.fortoszone.moviedb.utils.*
import com.fortoszone.moviedb.viewmodel.NowPlayingMovieAdapter
import com.fortoszone.moviedb.viewmodel.PopularMovieAdapter
import com.fortoszone.moviedb.viewmodel.TopRatedMovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvPopularMovie: RecyclerView
    private lateinit var rvTopRatedMovie: RecyclerView
    private lateinit var rvNowPlayingMovie: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvPopularMovie = binding.rvPopularMovie
        rvPopularMovie.setHasFixedSize(true)

        rvTopRatedMovie = binding.rvTopRatedMovie
        rvTopRatedMovie.setHasFixedSize(true)

        rvNowPlayingMovie = binding.rvNowPlayingMovie
        rvNowPlayingMovie.setHasFixedSize(true)

        loadRecyclerView()

    }

    private fun loadPopularMovie(callback: (List<Movie>) -> Unit) {
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
                    Toast.makeText(this@MainActivity, "An error has occured", Toast.LENGTH_LONG)
                        .show();
                }
            })
    }

    private fun loadRecyclerView() {
        rvPopularMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loadPopularMovie { movies: List<Movie> ->
            rvPopularMovie.adapter = PopularMovieAdapter(movies)
        }

        rvTopRatedMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loadTopRatedMovies { movies: List<Movie> ->
            rvTopRatedMovie.adapter = TopRatedMovieAdapter(movies)
        }

        rvNowPlayingMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loadNowPlayingMovies { movies: List<Movie> ->
            rvNowPlayingMovie.adapter = NowPlayingMovieAdapter(movies)
        }
    }
}

private fun loadTopRatedMovies(callback: (List<Movie>) -> Unit) {
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

            }
        })
}

private fun loadNowPlayingMovies(callback: (List<Movie>) -> Unit) {
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

            }
        })
}