package com.fortoszone.moviedb.di

import android.content.Context
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.model.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance()

        return MovieRepository.getInstance(remoteDataSource)
    }
}