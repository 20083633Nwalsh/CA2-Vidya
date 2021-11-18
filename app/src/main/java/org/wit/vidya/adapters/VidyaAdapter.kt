package org.wit.vidya.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.vidya.databinding.CardVidyaBinding
import org.wit.vidya.models.VidyaModel

class VidyaAdapter constructor(private var games: List<VidyaModel>) :
    RecyclerView.Adapter<VidyaAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVidyaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val vidya = games[holder.adapterPosition]
        holder.bind(vidya)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder(private val binding : CardVidyaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vidya: VidyaModel) {
            binding.vidyaTitle.text = vidya.title
            binding.description.text = vidya.description
        }
    }
}