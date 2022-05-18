package com.example.pokemon.ui.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.pokemon.databinding.FragmentPokemonDetailBinding
import com.example.pokemon.ui.viewmodel.PokemonDetailViewModel
import com.example.pokemon.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val args: PokemonDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<PokemonDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            detailName.text = args.pokemon.name
            Glide.with(root)
                .load(args.pokemon.getPokemonImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(pokemonImage)
        }
        loadPokemonDetail()
    }

    private fun loadPokemonDetail() {
        lifecycleScope.launch {
            viewModel.getPokemonDetail(args.pokemon.url).collect { result ->
                when (result) {
                    is ResultType.Loading -> {
                        binding.progress.visible()
                    }
                    is ResultType.Success -> {
                        binding.apply {
                            detailHeight.text = "${result.value.height.getDiv()} $PREFIX_HEIGHT"
                            detailWeight.text = "${result.value.weight.getDiv()} $PREFIX_WEIGHT"
                            progress.gone()
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    companion object {

    }
}