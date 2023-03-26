package com.fortoszone.moviedb.di

import android.content.Context
import com.fortoszone.moviedb.model.MovieRepository
import com.fortoszone.moviedb.model.local.LocalDataSource
import com.fortoszone.moviedb.model.local.room.MovieDatabase
import com.fortoszone.moviedb.model.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())

        return MovieRepository.getInstance(remoteDataSource, localDataSource)
    }
}