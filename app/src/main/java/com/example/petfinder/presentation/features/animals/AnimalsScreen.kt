package com.example.petfinder.presentation.features.animals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petfinder.databinding.FragmentAnimalsBinding
import com.example.petfinder.domain.exception.AppException.UnknownException
import com.example.petfinder.domain.model.Animal
import com.example.petfinder.domain.model.Animals
import com.example.petfinder.presentation.features.animals.AnimalsScreenDirections.Companion.toAnimalDetailsScreen
import com.example.petfinder.presentation.uirendererror.UiErrorHandler
import com.example.petfinder.util.Resource
import com.example.petfinder.util.Resource.Content
import com.example.petfinder.util.Resource.Error
import com.example.petfinder.util.Resource.Loading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimalsScreen : Fragment() {

    private var binding: FragmentAnimalsBinding? = null
    private val viewModel by viewModels<AnimalsViewModel>()

    @Inject
    lateinit var uiErrorHandler: UiErrorHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimalsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerList()
        observeUiState()
        generateState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observeUiState() {
        viewModel.animalsLiveData.observe(viewLifecycleOwner) {
            renderUiData(it)
        }
    }

    private fun generateState() {
        viewModel.getAnimalsBy(animalType = ANIMAL_TYPE)
    }

    private fun initRecyclerList() {
        val recyclerViewAdapter = AnimalsRecyclerViewAdapter(
            mutableListOf(),
            ::navigateToAnimalDetailsScreen
        )
        binding?.animalList?.adapter = recyclerViewAdapter
        binding?.animalList?.layoutManager = LinearLayoutManager(
            binding?.root?.context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun renderUiData(uiData: Resource<Animals>) {
        when (uiData) {
            is Loading -> {
                binding?.progressBar?.visibility = if (uiData.isLoading) View.VISIBLE else View.GONE
            }

            is Content -> (binding?.animalList?.adapter as? AnimalsRecyclerViewAdapter)?.updateAnimalList(
                animals = uiData.data.animals
            )

            is Error -> uiErrorHandler.renderError(
                error = uiData.exception ?: UnknownException(),
                context = requireContext()
            )
        }
    }

    private fun navigateToAnimalDetailsScreen(animal: Animal) {
        findNavController().navigate(toAnimalDetailsScreen(animal))
    }

    companion object {
        private const val ANIMAL_TYPE = "dog"
    }
}