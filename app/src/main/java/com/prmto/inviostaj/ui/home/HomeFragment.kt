package com.prmto.inviostaj.ui.home

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
import com.prmto.inviostaj.databinding.FragmentHomeBinding
import com.prmto.inviostaj.ui.adapter.MovieAdapter
import com.prmto.inviostaj.ui.adapter.PaginationScrollListener
import com.prmto.inviostaj.ui.adapter.viewHolder.listener.MovieItemClickListener
import com.prmto.inviostaj.ui.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var homeBinding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)
        homeBinding?.lifecycleOwner = viewLifecycleOwner
        homeBinding?.viewModel = homeViewModel
        setupRecyclerViewAndAdapter()
        addScrollListener()
        collectState()
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.state.collectLatest { uiState ->
                    movieAdapter.submitList(uiState.movies)
                    favoriteViewModel.updateIsFavoriteMovie(
                        uiState.movies,
                        updatedMovies = {
                            homeViewModel.updateIsFavoriteMovie(it)
                        }
                    )
                }
            }
        }
    }

    private fun addScrollListener() {
        homeBinding?.rvHomeMovieList?.addOnScrollListener(object :
            PaginationScrollListener(homeBinding?.rvHomeMovieList?.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                homeViewModel.fetchMovies()
            }

            override fun isLastPage() = homeViewModel.state.value.isLastPage

            override fun isLoading() = homeViewModel.state.value.isLoading
        })
    }

    private fun setupRecyclerViewAndAdapter() {
        movieAdapter = MovieAdapter(object : MovieItemClickListener {
            override fun onToggleFavoriteClicked(movie: Movie) {
                favoriteViewModel.toggleFavoriteMovie(movie)
                favoriteViewModel.updateIsFavoriteMovie(
                    homeViewModel.state.value.movies,
                    updatedMovies = {
                        homeViewModel.updateIsFavoriteMovie(it)
                    }
                )
            }

            override fun onMovieClicked(movieId: Int) {
                val action = MainNavGraphDirections.actionGlobalDetail(movieId)
                findNavController().navigate(action)
            }
        })

        homeBinding?.let { binding ->
            binding.rvHomeMovieList.adapter = movieAdapter
        }
    }

    override fun onDestroyView() {
        homeBinding = null
        super.onDestroyView()
    }
}