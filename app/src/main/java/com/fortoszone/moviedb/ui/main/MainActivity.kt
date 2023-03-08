package com.fortoszone.moviedb.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.adapter.NowPlayingMovieAdapter
import com.fortoszone.moviedb.adapter.PopularMovieAdapter
import com.fortoszone.moviedb.adapter.TopRatedMovieAdapter
import com.fortoszone.moviedb.databinding.ActivityMainBinding
import com.fortoszone.moviedb.model.Movie
import com.fortoszone.moviedb.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvPopularMovie: RecyclerView
    private lateinit var rvTopRatedMovie: RecyclerView
    private lateinit var rvNowPlayingMovie: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = MainViewModel()

        rvPopularMovie = binding.rvPopularMovie
        rvPopularMovie.setHasFixedSize(true)

        rvTopRatedMovie = binding.rvTopRatedMovie
        rvTopRatedMovie.setHasFixedSize(true)

        rvNowPlayingMovie = binding.rvNowPlayingMovie
        rvNowPlayingMovie.setHasFixedSize(true)

        loadRecyclerView()

    }

    private fun loadRecyclerView() {
        rvPopularMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainViewModel.loadPopularMovie(this) { movies: List<Movie> ->
            rvPopularMovie.adapter = PopularMovieAdapter(movies)
        }

        rvTopRatedMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainViewModel.loadTopRatedMovies(this) { movies: List<Movie> ->
            rvTopRatedMovie.adapter = TopRatedMovieAdapter(movies)
        }

        rvNowPlayingMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainViewModel.loadNowPlayingMovies(this) { movies: List<Movie> ->
            rvNowPlayingMovie.adapter = NowPlayingMovieAdapter(movies)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

