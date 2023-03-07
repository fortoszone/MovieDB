package com.fortoszone.moviedb.ui.detail

import FavoriteHelper
import android.content.ContentValues
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.fort0.githubuserapp.db.FavoriteContract
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ActivityDetailBinding
import com.fortoszone.moviedb.model.Movie
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(EXTRA_DETAILS) as Movie
        }

        if (movie != null) {
            binding.tvMovieTitle.text = movie.name
            binding.tvMovieReleaseDate.text = movie.releaseDate
            binding.tvMovieDesc.text = movie.overview
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.backdrop)
                .into(binding.imgMovie)
        } else {
            Toast.makeText(this, "Data belum masuk", Toast.LENGTH_SHORT).show()
        }

        checkIsFavorite()
        binding.fabFavorites.setOnClickListener {
            if (!isFavorite) {
                addToFavorite()
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.baseline_favorite_24
                    )
                )

            } else {
                removeFavorite()
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.baseline_favorite_border_24
                    )
                )
            }
        }
    }

    companion object {
        const val EXTRA_DETAILS = "extra_details"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
//                Intent(this@DetailActivity, FavoriteActivity::class.java).apply {
//                    startActivity(this)
//                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(EXTRA_DETAILS) as Movie
        }
        val favoriteHelper = FavoriteHelper(this)
        val values = ContentValues()

        favoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        values.put(FavoriteContract.FavoriteColumns.COLUMN_NAME_ID, movie!!.id)
        favoriteHelper.insert(values)
        favoriteHelper.close()

        Toast.makeText(this, "${movie.id} - added to favorite", Toast.LENGTH_SHORT).show()
        isFavorite = true
    }

    private fun removeFavorite() {
        val movie = intent.getParcelableExtra<Movie>(EXTRA_DETAILS) as Movie
        val favoriteHelper = FavoriteHelper(this)
        val values = ContentValues()

        favoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        favoriteHelper.deleteById(movie.id)
        favoriteHelper.insert(values)
        favoriteHelper.close()

        Toast.makeText(this, "${movie.id} - removed from favorite", Toast.LENGTH_SHORT).show()
        isFavorite = false

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun checkIsFavorite() {
        val movie = intent.getParcelableExtra<Movie>(EXTRA_DETAILS) as Movie
        val favoriteHelper = FavoriteHelper(this)

        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                favoriteHelper.getInstance(applicationContext)
                favoriteHelper.open()

                val cursor = favoriteHelper.queryById(movie.id)
                MappingHelper.mapCursorToObject(cursor)
            }

            val favPointer = deferredNotes.await()
            if (favPointer.id == movie.id) {
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity, R.drawable.baseline_favorite_24
                    )
                )
                isFavorite = true


            } else {
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity, R.drawable.baseline_favorite_border_24
                    )
                )
                isFavorite = false
            }
            favoriteHelper.close()

        }
    }

    object MappingHelper {
        fun mapCursorToObject(favCursor: Cursor?): Movie {
            var favoriteList = Movie()
            favCursor?.apply {
                if (favCursor.moveToFirst()) {
                    val id =
                        favCursor.getString(favCursor.getColumnIndexOrThrow(FavoriteContract.FavoriteColumns.COLUMN_NAME_ID))
                    favoriteList = Movie(id = id)
                    favCursor.close()
                }
            }
            return favoriteList
        }
    }
}