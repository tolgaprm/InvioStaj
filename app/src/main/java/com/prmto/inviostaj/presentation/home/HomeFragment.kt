package com.prmto.inviostaj.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentHomeBinding
import com.prmto.inviostaj.presentation.home.adapter.MoviePagingAdapter
import com.prmto.inviostaj.util.isErrorWithLoadState
import com.prmto.inviostaj.util.isLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var homeBinding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var moviePagingAdapter: MoviePagingAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        homeBinding = binding

        setupRecyclerViewAndAdapter()
        addLoadStateListener()
        collectTopRatedMovies()
        collectHomeUiState()
        addListenerToBtnTryAgain()
    }

    private fun addListenerToBtnTryAgain() {
        homeBinding?.let { homeBinding ->
            homeBinding.errorLayout.btnErrorLayoutTryAgain.setOnClickListener {
                moviePagingAdapter.retry()
            }
        }
    }

    // Collect edilen yerde view ayarlama işlemleri yapılmamalıdır diye bir açıklama var
    // geliştirme kurallarında.
    // Bu işlemleri nasıl yapmalıyım? -- Bunun için databinding kullanarak mı yapayım?
    private fun collectHomeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle = lifecycle).collectLatest { homeUiState ->
                homeBinding?.let { homeBinding ->
                    homeBinding.progressBar.isVisible = homeUiState.isLoading
                    homeBinding.rvHomeMovieList.isVisible = !homeUiState.isLoading
                    homeBinding.rvHomeMovieList.isVisible = homeUiState.isError
                    homeBinding.errorLayout.root.isVisible = !homeUiState.isError
                }
            }
        }
    }

    private fun setupRecyclerViewAndAdapter() {
        moviePagingAdapter = MoviePagingAdapter(
            onToggleFavoriteClicked = { movie ->
                viewModel.toggleFavoriteMovie(movie = movie)
            }
        )

        homeBinding?.let {
            with(it.rvHomeMovieList) {
                adapter = moviePagingAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun collectTopRatedMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topRatedMoviesPagingData.collectLatest { moviePagingData ->
                    moviePagingAdapter.submitData(moviePagingData)
                }
            }
        }
    }

    private fun addLoadStateListener() {
        moviePagingAdapter.addLoadStateListener { combinedLoadStates ->
            val isError = combinedLoadStates.isErrorWithLoadState()?.let {
                false
            } ?: true
            viewModel.updateErrorState(isError = isError)
            viewModel.updateLoadingState(isLoading = combinedLoadStates.isLoading())
        }
    }

    override fun onDestroyView() {
        homeBinding = null
        super.onDestroyView()
    }
}