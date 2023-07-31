package com.prmto.inviostaj.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var homeBinding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val moviePagingAdapter by lazy { MoviePagingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        homeBinding = binding

        setupRecyclerView()
        collectTopRatedMovies()
    }

    private fun setupRecyclerView() {
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

    override fun onDestroyView() {
        homeBinding = null
        super.onDestroyView()
    }
}