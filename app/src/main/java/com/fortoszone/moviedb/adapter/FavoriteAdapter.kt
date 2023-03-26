package com.fortoszone.moviedb.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.databinding.FavoriteRowBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.ui.detail.DetailActivity

class FavoriteAdapter() :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var movies = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_row, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return this.movies.size
    }

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavoriteRowBinding.bind(view)
        fun bind(movie: Movie) {
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .into(binding.imgMovie)

            binding.tvMovieReleaseDate.text = movie.releaseDate
            binding.tvMovieTitle.text = movie.title

            with(itemView) {
                binding.movie.setOnClickListener {
                    val moveActivity = Intent(itemView.context, DetailActivity::class.java)
                    moveActivity.putExtra(DetailActivity.EXTRA_DETAILS, movie)
                    itemView.context.startActivity(moveActivity)
                }
            }
        }
    }
    fun setData(movie: List<Movie>) {
        movie.isEmpty()
        this.movies = movie
        notifyDataSetChanged()
    }
}