package com.fortoszone.moviedb.ui.favorite

import FavoriteHelper
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fort0.githubuserapp.db.FavoriteContract
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.adapter.FavoriteAdapter
import com.fortoszone.moviedb.databinding.ActivityFavoriteBinding
import com.fortoszone.moviedb.db.DatabaseMovie.UserColumns.Companion.CONTENT_URI
import com.fortoszone.moviedb.model.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private var movies: ArrayList<Movie> = arrayListOf()
    private lateinit var rvFavoriteMovie: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = supportActionBar
        toolbar?.title = getString(R.string.favorite_title)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        val favoriteHelper = FavoriteHelper(this)
        favoriteHelper.open()
        if (savedInstanceState == null) {
            loadFavoriteMovie(this)
            contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        } else {
            val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelableArrayList(EXTRA_STATE, Movie::class.java)
            } else {
                savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            }
            if (movie != null) {
                adapter.movies = movie
            }
        }

        rvFavoriteMovie = binding.rvFavoriteMovie
        rvFavoriteMovie.setHasFixedSize(true)
        showRecyclerView()
        contentResolver.notifyChange(CONTENT_URI, null)
    }

    private fun showRecyclerView() {
        binding.rvFavoriteMovie.layoutManager = LinearLayoutManager(this)
        val adapter = FavoriteAdapter(this, movies)
        binding.rvFavoriteMovie.adapter = adapter
    }

    override fun onRestart() {
        super.onRestart()
        movies.clear()
        loadFavoriteMovie(this)
        contentResolver.notifyChange(CONTENT_URI, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        movies.clear()
        val favoriteHelper = FavoriteHelper(this)
        favoriteHelper.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val adapter = FavoriteAdapter(this, movies)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.movies)
    }

    private fun loadFavoriteMovie(context: Context) {
        val favoriteHelper = FavoriteHelper(context)
        val adapter = FavoriteAdapter(context, movies)
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                favoriteHelper.open()
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favoriteMovie = deferredNotes.await()

            if (favoriteMovie.size > 0) {
                adapter.movies = favoriteMovie
                for (i in 0 until favoriteMovie.size) {
                    Toast.makeText(context, favoriteMovie[i].id, Toast.LENGTH_SHORT)
                        .show()
                    getFavoriteMovie(
                        context,
                        favoriteMovie[i].id
                    )

                }

                Toast.makeText(
                    context,
                    "Favorite Count : ${favoriteMovie.size}",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                adapter.movies = ArrayList()
                Toast.makeText(context, "Favorite Count : 0", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    object MappingHelper {
        fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<Movie> {
            val favoriteList = ArrayList<Movie>()

            favCursor?.apply {
                while (moveToNext()) {
                    val favoriteMovieId =
                        getString(getColumnIndexOrThrow(FavoriteContract.FavoriteColumns.COLUMN_NAME_ID))
                    favoriteList.add(Movie(id = favoriteMovieId))
                }
            }
            return favoriteList
        }
    }

    private fun getFavoriteMovie(context: Context, id: String) {
        val client = AsyncHttpClient()
        val url =
            "https://api.themoviedb.org/3/movie/$id?api_key=078e8fe79377bcac312b276a6f7ed8fa"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val response = String(responseBody)
                parseJsonData(response)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(context, statusCode.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun parseJsonData(response: String) {
        try {
            val jsonObject = JSONObject(response)

            val movie = Movie()
            movie.id = jsonObject.getString("id")
            movie.posterPath = jsonObject.getString("poster_path")
            movie.name = jsonObject.getString("original_title")
            movie.backdrop = jsonObject.getString("backdrop_path")
            movie.releaseDate = jsonObject.getString("release_date")
            movie.overview = jsonObject.getString("overview")

            movies.add(movie)
            showRecyclerView()
            contentResolver.notifyChange(CONTENT_URI, null)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}