package com.fortoszone.moviedb.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.ActivityFavoriteBinding
import com.fortoszone.moviedb.viewmodel.PopularMovieAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

    }


}