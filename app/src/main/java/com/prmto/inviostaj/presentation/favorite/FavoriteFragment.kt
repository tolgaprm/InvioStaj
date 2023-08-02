package com.prmto.inviostaj.presentation.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentFavoriteBinding
import com.prmto.inviostaj.presentation.favorite.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var favoriteBinding: FragmentFavoriteBinding? = null

    private val viewModel: FavoriteViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        favoriteBinding = binding
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupRecyclerViewAndAdapter()

    }

    private fun setupRecyclerViewAndAdapter() {
        movieAdapter = MovieAdapter(
            onToggleFavoriteClick = {
                viewModel.toggleFavoriteMovie(it)
            }
        )
        favoriteBinding?.let {
            with(it.rvFavoriteMovie) {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        favoriteBinding = null
        super.onDestroyView()
    }
}