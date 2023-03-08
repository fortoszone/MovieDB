package com.fortoszone.moviedb.ui.detail

import FavoriteHelper
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.fort0.githubuserapp.db.FavoriteContract
import com.fortoszone.moviedb.model.Movie

class DetailViewModel : ViewModel() {
    fun addToFavorite(context: Context, intent: Intent, applicationContext: Context) {
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

        Toast.makeText(context, "${movie.name} added to favorite", Toast.LENGTH_SHORT).show()
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

        Toast.makeText(context, "${movie.name} - removed from favorite", Toast.LENGTH_SHORT).show()
    }
}