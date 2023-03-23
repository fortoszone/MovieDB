package com.fortoszone.moviedb.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fortoszone.moviedb.di.Injection
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.ui.main.MainViewModel
import com.fortoszone.moviedb.ui.review.ReviewViewModel

class ViewModelFactory constructor(private val repository: MovieRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else if ((modelClass.isAssignableFrom(ReviewViewModel::class.java))) {
            ReviewViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }
    }
}