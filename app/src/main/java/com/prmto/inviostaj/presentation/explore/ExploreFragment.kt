package com.prmto.inviostaj.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentExploreBinding
import com.prmto.inviostaj.presentation.adapter.MovieAdapter
import com.prmto.inviostaj.presentation.adapter.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private var exploreBinding: FragmentExploreBinding? = null

    private val viewModel: ExploreViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentExploreBinding.bind(view)
        exploreBinding = binding

        setupRecyclerViewAndAdapter()
        addScrollListener()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exploreUiState.collectLatest {
                    movieAdapter.submitList(it.movies)
                }
            }
        }
    }

    private fun addScrollListener() {
        val recyclerView = exploreBinding?.rvExploreMovies ?: return
        recyclerView.addOnScrollListener(object :
            PaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.fetchMovies()
            }

            override fun isLastPage() = viewModel.exploreUiState.value.isLastPage

            override fun isLoading() = viewModel.exploreUiState.value.isLoading
        })
    }

    private fun setupRecyclerViewAndAdapter() {
        movieAdapter = MovieAdapter(
            onToggleFavoriteClick = { movie ->
                viewModel.toggleFavoriteMovie(movie = movie)
            },
            onMovieClick = { movieId ->
                val action =
                    ExploreFragmentDirections.actionExploreFragmentToDetailFragment(movieId)
                findNavController().navigate(action)
            }
        )

        exploreBinding?.let {
            with(it.rvExploreMovies) {
                adapter = movieAdapter
                layoutManager = GridLayoutManager(
                    requireContext(),
                    requireContext().resources.getInteger(R.integer.explore_fragment_grid_layout_span_count)
                )
            }
        }
    }

    override fun onDestroyView() {
        exploreBinding = null
        super.onDestroyView()
    }
}