package com.fortoszone.moviedb.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.MovieRowBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.ui.detail.DetailActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TopRatedMovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return TopRatedMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) {
        holder.bind(this.movies[position])
    }

    override fun getItemCount(): Int {
        return this.movies.size

    }

    inner class TopRatedMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MovieRowBinding.bind(view)
        fun bind(movie: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.imgMovie)
            binding.tvMovieTitle.text = movie.title

            val timeFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val date = LocalDate.parse(movie.releaseDate, timeFormat)
            binding.tvMovieReleaseDate.text = date.year.toString()

            with(itemView) {
                binding.cvMovie.setOnClickListener {
                    val moveActivity = Intent(itemView.context, DetailActivity::class.java)
                    moveActivity.putExtra(DetailActivity.EXTRA_DETAILS, movie)
                    itemView.context.startActivity(moveActivity)
                }
            }
        }
    }
}