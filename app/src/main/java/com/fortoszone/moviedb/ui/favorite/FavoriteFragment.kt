package com.fortoszone.moviedb.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.adapter.FavoriteAdapter
import com.fortoszone.moviedb.databinding.FragmentFavoriteBinding
import com.fortoszone.moviedb.model.local.entity.Movie
import com.fortoszone.moviedb.utils.ViewModelFactory

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private var movies: ArrayList<Movie> = arrayListOf()
    private lateinit var rvFavoriteMovie: RecyclerView
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        adapter = FavoriteAdapter()

        rvFavoriteMovie = binding.rvFavoriteMovie
        rvFavoriteMovie.setHasFixedSize(true)
        binding.rvFavoriteMovie.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteMovie.adapter = adapter

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(
            this, factory
        )[FavoriteViewModel::class.java]

        viewModel.getFavoriteMovieList().observe(
            viewLifecycleOwner
        ) { movies -> adapter.setData(movies) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteMovieList().observe(
            viewLifecycleOwner
        ) { movies -> adapter.setData(movies) }
    }
}