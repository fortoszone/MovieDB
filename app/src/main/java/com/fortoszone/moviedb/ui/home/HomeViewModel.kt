package com.fortoszone.moviedb.ui.home

import androidx.lifecycle.ViewModel
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.model.local.entity.Movie

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    fun loadPopularMovie(callback: (List<Movie>) -> Unit) = repository.getPopularMovies(callback)

    fun loadTopRatedMovies(callback: (List<Movie>) -> Unit) = repository.getTopRatedMovies(callback)

    fun loadNowPlayingMovies(callback: (List<Movie>) -> Unit) =
        repository.getNowPlayingMovies(callback)
}