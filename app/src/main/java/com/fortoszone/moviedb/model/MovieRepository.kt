package com.fortoszone.moviedb.model

import android.content.Intent
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.model.local.entity.Review
import com.fortoszone.moviedb.model.remote.RemoteDataSource

class MovieRepository(private val remoteDataSource: RemoteDataSource) {
    fun getPopularMovies(callback: (List<Movie>) -> Unit) =
        remoteDataSource.getPopularMovie(callback)

    fun getTopRatedMovies(callback: (List<Movie>) -> Unit) =
        remoteDataSource.getTopRatedMovie(callback)

    fun getNowPlayingMovies(callback: (List<Movie>) -> Unit) =
        remoteDataSource.getNowPlayingMovie(callback)

    fun getMovieReviews(intent: Intent, callback: (List<Review>) -> Unit) =
        remoteDataSource.getMovieReview(intent, callback)

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
//            localDataSource: LocalDataSource,
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource/*, localDataSource*/)
            }
    }

}