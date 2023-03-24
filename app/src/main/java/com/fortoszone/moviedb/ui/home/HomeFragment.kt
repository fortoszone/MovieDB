package com.fortoszone.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.adapter.NowPlayingMovieAdapter
import com.fortoszone.moviedb.adapter.PopularMovieAdapter
import com.fortoszone.moviedb.adapter.TopRatedMovieAdapter
import com.fortoszone.moviedb.databinding.FragmentHomeBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.utils.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvPopularMovie: RecyclerView
    private lateinit var rvTopRatedMovie: RecyclerView
    private lateinit var rvNowPlayingMovie: RecyclerView
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(
            this, factory
        )[HomeViewModel::class.java]

        rvPopularMovie = binding.rvPopularMovie
        rvPopularMovie.setHasFixedSize(true)

        rvTopRatedMovie = binding.rvTopRatedMovie
        rvTopRatedMovie.setHasFixedSize(true)

        rvNowPlayingMovie = binding.rvNowPlayingMovie
        rvNowPlayingMovie.setHasFixedSize(true)

        loadRecyclerView()

        return binding.root
    }

    private fun loadRecyclerView() {
        rvPopularMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTopRatedMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvNowPlayingMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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
}