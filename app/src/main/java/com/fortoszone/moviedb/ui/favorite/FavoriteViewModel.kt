package com.fortoszone.moviedb.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.model.local.entity.Movie

class FavoriteViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getFavoriteMovieList(): LiveData<List<Movie>> = repository.getFavoriteMovie
}