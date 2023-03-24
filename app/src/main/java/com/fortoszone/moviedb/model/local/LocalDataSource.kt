package com.fortoszone.moviedb.model.local

import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.model.local.room.MovieDao

class LocalDataSource private constructor(
    private val movieDao: MovieDao
) {
    fun getFavoriteMovie() = movieDao.getFavoriteMovie()
//    fun movieIsFavorite(id: String, isFavorite: Boolean) = movieDao.movieFavoriteState(id, isFavorite)
    fun addMovieToFavorite(movie: Movie) = movieDao.addMovieToFavorite(movie)

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }
}