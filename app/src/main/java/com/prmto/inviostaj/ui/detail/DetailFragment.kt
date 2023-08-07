package com.prmto.inviostaj.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prmto.inviostaj.R
import com.prmto.inviostaj.databinding.FragmentDetailBinding
import com.prmto.inviostaj.util.Constants.IMDB_TITLE_BASE_URl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail), ImdbIconClickListener {
    private var detailBinding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        detailBinding = binding
        binding.lifecycleOwner = viewLifecycleOwner
        binding.imdbClickListener = this
        binding.viewModel = viewModel
    }

    override fun onDestroyView() {
        detailBinding = null
        super.onDestroyView()
    }

    override fun onImdbIconClick(imdbId: String?) {
        if (imdbId != null) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$IMDB_TITLE_BASE_URl/$imdbId")
            }
            startActivity(intent)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.the_imdb_id_does_not_exists), Toast.LENGTH_SHORT
            ).show()
        }
    }
}