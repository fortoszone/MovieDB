package com.fortoszone.moviedb.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ActivityDetailBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.tmdb_logo_alt_short)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this, factory
        )[DetailViewModel::class.java]

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DETAILS, Movie::class.java)
        } else {
            intent.getParcelableExtra<Movie>(EXTRA_DETAILS) as Movie
        }

//        checkMovieFavorite()

        if (movie != null) {
            binding.tvMovieTitle.text = movie.title
            binding.tvMovieReleaseDate.text = movie.releaseDate
            binding.tvMovieDesc.text = movie.overview
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.backdrop)
                .into(binding.imgMovie)
        } else {
            Toast.makeText(this, "Data is not retrieved yet", Toast.LENGTH_SHORT).show()
        }

        val id = movie!!.id

        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkMovieIsFavorite(id)
            withContext(Dispatchers.Main) {
                isCheck = if (count > 0.toString()) {
                    binding.fabFavorites.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity, R.drawable.baseline_favorite_24
                        )
                    )
                    true
                } else {
                    binding.fabFavorites.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity, R.drawable.baseline_favorite_border_24
                        )
                    )
                    false
                }
            }
        }

        binding.fabFavorites.setOnClickListener {
            isCheck = !isCheck
            if (isCheck) {
                viewModel.addMovieToFavorite(movie)
                Toast.makeText(this, "${movie.title} added to favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteMovieFromFavorite(movie)
                Toast.makeText(this, "${movie.title} removed from favorite", Toast.LENGTH_SHORT)
                    .show()
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
}