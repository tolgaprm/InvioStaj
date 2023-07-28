package com.prmto.inviostaj.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var favoriteBinding: FragmentFavoriteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        favoriteBinding = binding
    }

    override fun onDestroyView() {
        favoriteBinding = null
        super.onDestroyView()
    }
}