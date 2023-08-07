package com.prmto.inviostaj.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.prmto.inviostaj.MainNavGraphDirections
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentFavoriteBinding
import com.prmto.inviostaj.ui.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var favoriteBinding: FragmentFavoriteBinding? = null
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteBinding = FragmentFavoriteBinding.bind(view)
        favoriteBinding?.lifecycleOwner = viewLifecycleOwner
        favoriteBinding?.viewModel = viewModel
        setupRecyclerViewAndAdapter()
        submitFavoriteMoviesToAdapter()
        viewModel.fetchFavoriteMovies()
    }

    private fun submitFavoriteMoviesToAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteUiState.collectLatest {
                    movieAdapter.submitList(it.favoriteMovies)
                }
            }
        }
    }

    private fun setupRecyclerViewAndAdapter() {
        movieAdapter = MovieAdapter(
            onToggleFavoriteClick = {
                viewModel.toggleFavoriteMovie(it)
            },
            onMovieClick = { movieId ->
                val action = MainNavGraphDirections.actionGlobalDetail(movieId)
                findNavController().navigate(action)
            }
        )
        favoriteBinding?.rvFavoriteMovie?.adapter = movieAdapter
    }

    override fun onDestroyView() {
        favoriteBinding = null
        super.onDestroyView()
    }
}