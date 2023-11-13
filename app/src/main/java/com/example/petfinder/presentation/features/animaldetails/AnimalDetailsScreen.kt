package com.example.petfinder.presentation.features.animaldetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.petfinder.databinding.FragmentAnimalsDetailsBinding
import com.example.petfinder.domain.model.Animal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimalDetailsScreen : Fragment() {
    private var binding: FragmentAnimalsDetailsBinding? = null
    private lateinit var animal: Animal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimalsDetailsBinding.inflate(inflater, container, false)

        arguments?.let { bundle ->
            animal = AnimalDetailsScreenArgs.fromBundle(bundle).animal
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderUi()
    }

    private fun renderUi() {
        binding?.apply {
            name.text = animal.name
            breed.text = animal.breeds.primary
            size.text = animal.size
            gender.text = animal.gender
            status.text = animal.status
            distance.text = animal.distance
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}