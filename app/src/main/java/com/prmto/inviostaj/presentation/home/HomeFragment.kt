package com.prmto.inviostaj.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentHomeBinding
import com.prmto.inviostaj.presentation.adapter.MovieAdapter
import com.prmto.inviostaj.presentation.adapter.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var homeBinding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        homeBinding = binding

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupRecyclerViewAndAdapter()
        addScrollListener()
        submitMoviesToAdapter()
    }

    private fun submitMoviesToAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    movieAdapter.submitList(it.movies)
                }
            }
        }
    }

    private fun addScrollListener() {
        val recyclerView = homeBinding?.rvHomeMovieList ?: return
        recyclerView.addOnScrollListener(object :
            PaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.fetchMovies()
            }

            override fun isLastPage() = viewModel.state.value.isLastPage

            override fun isLoading() = viewModel.state.value.isLoading
        })
    }

    private fun setupRecyclerViewAndAdapter() {
        movieAdapter = MovieAdapter(
            onToggleFavoriteClick = { movie ->
                viewModel.toggleFavoriteMovie(movie = movie)
            },
            onMovieClick = { movieId ->
                Log.d("Invio", movieId.toString())
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
                findNavController().navigate(action)
            }
        )

        homeBinding?.let {
            with(it.rvHomeMovieList) {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        homeBinding = null
        super.onDestroyView()
    }
}