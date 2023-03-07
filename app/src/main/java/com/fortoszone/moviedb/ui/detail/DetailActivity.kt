package com.fortoszone.moviedb.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ActivityDetailBinding
import com.fortoszone.moviedb.model.Movie

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
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
}