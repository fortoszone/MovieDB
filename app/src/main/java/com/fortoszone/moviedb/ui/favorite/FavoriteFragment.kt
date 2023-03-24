package com.fortoszone.moviedb.ui.favorite

import FavoriteHelper
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fort0.githubuserapp.db.FavoriteContract
import com.fortoszone.moviedb.adapter.FavoriteAdapter
import com.fortoszone.moviedb.databinding.FragmentFavoriteBinding
import com.fortoszone.moviedb.db.DatabaseMovie
import com.fortoszone.moviedb.model.local.entity.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private var movies: ArrayList<Movie> = arrayListOf()
    private lateinit var rvFavoriteMovie: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
            }
        }

        activity?.contentResolver?.registerContentObserver(
            DatabaseMovie.UserColumns.CONTENT_URI,
            true,
            myObserver
        )

        val favoriteHelper = FavoriteHelper(requireContext())
        favoriteHelper.open()
        if (savedInstanceState == null) {
            loadFavoriteMovie(requireContext())
            activity?.contentResolver?.registerContentObserver(
                DatabaseMovie.UserColumns.CONTENT_URI,
                true,
                myObserver
            )

        } else {
            val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelableArrayList(
                    EXTRA_STATE,
                    Movie::class.java
                )
            } else {
                savedInstanceState.getParcelableArrayList(EXTRA_STATE)
            }
            if (movie != null) {
                val adapter = FavoriteAdapter(requireContext(), movies)
                adapter.movies = movie
            }
        }

        rvFavoriteMovie = binding.rvFavoriteMovie
        rvFavoriteMovie.setHasFixedSize(true)
        showRecyclerView()
        activity?.contentResolver?.notifyChange(DatabaseMovie.UserColumns.CONTENT_URI, null)

        return binding.root
    }

    private fun showRecyclerView() {
        binding.rvFavoriteMovie.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FavoriteAdapter(requireContext(), movies)
        binding.rvFavoriteMovie.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        movies.clear()
        loadFavoriteMovie(requireContext())
        activity?.contentResolver?.notifyChange(DatabaseMovie.UserColumns.CONTENT_URI, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        movies.clear()
        val favoriteHelper = FavoriteHelper(requireContext())
        favoriteHelper.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val adapter = FavoriteAdapter(requireContext(), movies)
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
            movie.title = jsonObject.getString("original_title")
            movie.backdrop = jsonObject.getString("backdrop_path")
            movie.releaseDate = jsonObject.getString("release_date")
            movie.overview = jsonObject.getString("overview")

            movies.add(movie)
            showRecyclerView()
            activity?.contentResolver?.notifyChange(DatabaseMovie.UserColumns.CONTENT_URI, null)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}