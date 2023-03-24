package com.fortoszone.moviedb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.model.local.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {
    fun addMovieToFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovieToFavorite(movie)
        }
    }

    fun deleteMovieFromFavorite(id: String) {

    }
/*    fun addToFavorite(context: Context, intent: Intent, applicationContext: Context) {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DetailActivity.EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(DetailActivity.EXTRA_DETAILS) as Movie
        }
        val favoriteHelper = FavoriteHelper(context)
        val values = ContentValues()

        favoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        values.put(FavoriteContract.FavoriteColumns.COLUMN_NAME_ID, movie!!.id)
        favoriteHelper.insert(values)
        favoriteHelper.close()

        Toast.makeText(context, "${movie.title} added to favorite", Toast.LENGTH_SHORT).show()
    }

    fun removeFavorite(context: Context, intent: Intent, applicationContext: Context) {
        val movie = intent.getParcelableExtra<Movie>(DetailActivity.EXTRA_DETAILS) as Movie
        val favoriteHelper = FavoriteHelper(context)
        val values = ContentValues()

        favoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        favoriteHelper.deleteById(movie.id)
        favoriteHelper.insert(values)
        favoriteHelper.close()

        Toast.makeText(context, "${movie.title} - removed from favorite", Toast.LENGTH_SHORT).show()
    }*/
}