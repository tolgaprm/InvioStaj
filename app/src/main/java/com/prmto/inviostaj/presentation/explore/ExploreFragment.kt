package com.prmto.inviostaj.presentation.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentExploreBinding


class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private var exploreBinding: FragmentExploreBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentExploreBinding.bind(view)
        exploreBinding = binding
    }

    override fun onDestroyView() {
        exploreBinding = null
        super.onDestroyView()
    }

}