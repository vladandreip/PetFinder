package com.example.petfinder.presentation.features.animals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.petfinder.R
import com.example.petfinder.databinding.ItemAnimalBinding
import com.example.petfinder.domain.model.Animal
import java.util.Locale

class AnimalsRecyclerViewAdapter(
    private val animals: MutableList<Animal>,
    private val onItemNavigationCallback: (animal: Animal) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAnimalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )

    override fun getItemCount(): Int = animals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemNavigationCallback(animals[position])
        }
        ItemAnimalBinding.bind(holder.itemView).apply {
            name.text = animals[position].name
            if (animals[position].gender.lowercase(Locale.ROOT) == MALE) {
                animalLayout.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_blue_bright)
                )
                animalImage.setImageResource(R.drawable.male)
            } else {
                animalLayout.setBackgroundColor(
                    holder.itemView.context.getColor(R.color.pink)
                )
                animalImage.setImageResource(R.drawable.female)
            }
        }
    }

    fun updateAnimalList(animals: List<Animal>) {
        val diffCallback = AnimalDiffCallback(this.animals, animals)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.animals.clear()
        this.animals.addAll(animals)
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val MALE = "male"
    }
}

class AnimalDiffCallback(
    private val oldList: List<Animal>,
    private val newList: List<Animal>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

}