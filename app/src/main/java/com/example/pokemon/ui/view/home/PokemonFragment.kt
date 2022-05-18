package com.example.pokemon.ui.view.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pokemon.R
import com.example.pokemon.data.model.PokemonResult
import com.example.pokemon.databinding.FragmentPokemonBinding
import com.example.pokemon.ui.viewmodel.PokemonViewModel
import com.example.pokemon.util.gone
import com.example.pokemon.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PokemonViewModel>()

    private val adapter by lazy {
        PokemonAdapter(pokemon = ::onClickPokemonListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setRefresh()
    }

    private fun setAdapter() {
        binding.run {
            val gridLayoutManager = StaggeredGridLayoutManager(
                SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL
            )
            rvPokemon.layoutManager = gridLayoutManager
            rvPokemon.adapter = adapter
            getPokemonList(false)

            adapter.addLoadStateListener {
                when (it.refresh) {
                    is LoadState.Error -> {
                        progress.gone()
                        errorMessage.visible()
                        swipeRefresh.isRefreshing = false
                    }
                    is LoadState.Loading -> {
                        progress.visible()
                        errorMessage.gone()
                        swipeRefresh.isRefreshing = false
                    }
                    else -> {
                        progress.gone()
                        errorMessage.gone()
                        swipeRefresh.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun setRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getPokemonList(true)
        }
    }

    private fun getPokemonList(isRefresh: Boolean){
        lifecycleScope.launch {
            if (isRefresh) adapter.submitData(PagingData.empty())
            viewModel.getPokemon().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun onClickPokemonListener(pokemon: PokemonResult) {
        val actionFragment = PokemonFragmentDirections.actionPokemonFragmentToPokemonDetailFragment(pokemon)
        findNavController().navigate(actionFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}