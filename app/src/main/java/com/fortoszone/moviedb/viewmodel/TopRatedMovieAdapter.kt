package com.fortoszone.moviedb.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.MovieRowBinding
import com.fortoszone.moviedb.model.Movie

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
            binding.tvMovieTitle.text = movie.name
            binding.tvMovieReleaseDate.text = movie.releaseDate

//            with(itemView) {
//                binding.user.setOnClickListener {
//                    val moveActivity = Intent(itemView.context, DetailActivity::class.java)
//                    moveActivity.putExtra(DetailActivity.EXTRA_DETAILS, user)
//                    itemView.context.startActivity(moveActivity)
//                }
//            }
        }
    }
}