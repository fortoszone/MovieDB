package com.fortoszone.moviedb.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortoszone.moviedb.R
import com.fortoszone.moviedb.adapter.ReviewAdapter
import com.fortoszone.moviedb.model.Review


class ReviewFragment : Fragment() {
    private lateinit var rvMovieReview: RecyclerView
    private lateinit var reviewViewModel: ReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reviewViewModel = ReviewViewModel()
        val view: View = inflater.inflate(R.layout.fragment_review, container, false)
        rvMovieReview = view.findViewById(R.id.rv_movie_review) as RecyclerView
        rvMovieReview.setHasFixedSize(true)

        reviewViewModel.loadMovieReview(
            requireContext(),
            requireActivity().intent
        ) { review: List<Review> ->
            rvMovieReview.adapter = ReviewAdapter(review)
        }

        rvMovieReview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }
}
