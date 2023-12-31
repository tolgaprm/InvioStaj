package com.prmto.inviostaj.ui.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.prmto.inviostaj.MainNavGraphDirections
import com.prmto.inviostaj.R
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.databinding.FragmentExploreBinding
import com.prmto.inviostaj.ui.adapter.MovieAdapter
import com.prmto.inviostaj.ui.adapter.PaginationScrollListener
import com.prmto.inviostaj.ui.adapter.viewHolder.listener.MovieItemClickListener
import com.prmto.inviostaj.ui.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {
    private var exploreBinding: FragmentExploreBinding? = null
    private val exploreViewModel: ExploreViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exploreBinding = FragmentExploreBinding.bind(view)
        exploreBinding?.lifecycleOwner = viewLifecycleOwner
        exploreBinding?.viewModel = exploreViewModel
        setupRecyclerViewAndAdapter()
        addScrollListener()
        collectState()
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                exploreViewModel.exploreUiState.collectLatest { uiState ->
                    movieAdapter.submitList(uiState.movies)
                    favoriteViewModel.updateIsFavoriteMovie(
                        uiState.movies,
                        updatedMovies = {
                            exploreViewModel.updateIsFavoriteMovie(it)
                        }
                    )
                }
            }
        }
    }

    private fun addScrollListener() {
        exploreBinding?.rvExploreMovies?.addOnScrollListener(object :
            PaginationScrollListener(exploreBinding?.rvExploreMovies?.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                exploreViewModel.fetchMovies()
            }

            override fun isLastPage() = exploreViewModel.exploreUiState.value.isLastPage

            override fun isLoading() = exploreViewModel.exploreUiState.value.isLoading
        })
    }

    private fun setupRecyclerViewAndAdapter() {
        movieAdapter = MovieAdapter(
            object : MovieItemClickListener {
                override fun onToggleFavoriteClicked(movie: Movie) {
                    favoriteViewModel.toggleFavoriteMovie(movie)
                    favoriteViewModel.updateIsFavoriteMovie(
                        movies = exploreViewModel.exploreUiState.value.movies,
                        updatedMovies = {
                            exploreViewModel.updateIsFavoriteMovie(it)
                        }
                    )
                }

                override fun onMovieClicked(movieId: Int) {
                    val action = MainNavGraphDirections.actionGlobalDetail(movieId)
                    findNavController().navigate(action)
                }
            }
        )
        exploreBinding?.rvExploreMovies?.adapter = movieAdapter
    }

    override fun onDestroyView() {
        exploreBinding = null
        super.onDestroyView()
    }
}