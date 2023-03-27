package com.fortoszone.moviedb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.MovieRowPopularBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.ui.detail.DetailActivity

class PopularMovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_row_popular, parent, false)
        return PopularMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return this.movies.size
    }

    class PopularMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MovieRowPopularBinding.bind(view)
        fun bind(movie: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + movie.backdrop)
                .into(binding.imgMovie)
            binding.tvMovieTitle.text = movie.title


            binding.imgMovie.setOnClickListener {
                val moveActivity = Intent(itemView.context, DetailActivity::class.java)
                moveActivity.putExtra(DetailActivity.EXTRA_DETAILS, movie)
                itemView.context.startActivity(moveActivity)
            }
        }
    }
}