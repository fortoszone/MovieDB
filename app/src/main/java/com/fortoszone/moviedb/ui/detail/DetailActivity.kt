package com.fortoszone.moviedb.ui.detail

import FavoriteHelper
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private lateinit var dialog: BottomSheetDialog
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = DetailViewModel()

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
            Toast.makeText(this, "Data is not retrieved yet", Toast.LENGTH_SHORT).show()
        }

        checkIsFavorite()
        binding.fabFavorites.setOnClickListener {
            if (!isFavorite) {
                detailViewModel.addToFavorite(this, intent, applicationContext)
                isFavorite = true
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.baseline_favorite_24
                    )
                )

            } else {
                detailViewModel.removeFavorite(this, intent, applicationContext)
                binding.fabFavorites.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.baseline_favorite_border_24
                    )
                )
            }
        }
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
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
                showBottomSheet()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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