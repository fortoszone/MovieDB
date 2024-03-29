package com.fortoszone.moviedb.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fortoszone.moviedb.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.about_title)
    }
}