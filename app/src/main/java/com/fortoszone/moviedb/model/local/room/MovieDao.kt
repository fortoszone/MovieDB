package com.fortoszone.moviedb.model.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fortoszone.moviedb.model.local.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getFavoriteMovie(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovieToFavorite(movie: Movie)

    /*@Delete(onConflict = OnConflictStrategy.IGNORE)
    fun removeMovieFromFavorite(id: String)*/
}