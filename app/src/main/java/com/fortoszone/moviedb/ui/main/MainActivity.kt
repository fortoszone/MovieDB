package com.fortoszone.moviedb.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.adapter.NowPlayingMovieAdapter
import com.fortoszone.moviedb.adapter.PopularMovieAdapter
import com.fortoszone.moviedb.adapter.TopRatedMovieAdapter
import com.fortoszone.moviedb.databinding.ActivityMainBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.ui.favorite.FavoriteActivity
import com.fortoszone.moviedb.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvPopularMovie: RecyclerView
    private lateinit var rvTopRatedMovie: RecyclerView
    private lateinit var rvNowPlayingMovie: RecyclerView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.tmdb_logo_alt_short)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this, factory
        )[MainViewModel::class.java]

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
        rvTopRatedMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlayingMovie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.loadPopularMovie() { movies: List<Movie> ->
            rvPopularMovie.adapter = PopularMovieAdapter(movies)
        }

        viewModel.loadTopRatedMovies() { movies: List<Movie> ->
            rvTopRatedMovie.adapter = TopRatedMovieAdapter(movies)
        }

        viewModel.loadNowPlayingMovies() { movies: List<Movie> ->
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