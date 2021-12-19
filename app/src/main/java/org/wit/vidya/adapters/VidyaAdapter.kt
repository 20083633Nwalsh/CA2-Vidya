package org.wit.vidya.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.vidya.databinding.CardVidyaBinding
import org.wit.vidya.models.VidyaModel

interface VidyaListener {
    fun onVidyaClick(vidya: VidyaModel)
}

class VidyaAdapter constructor(private var games: List<VidyaModel>,
                                   private val listener: VidyaListener) :
    RecyclerView.Adapter<VidyaAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVidyaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val vidya = games[holder.adapterPosition]
        holder.bind(vidya, listener)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder(private val binding : CardVidyaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vidya: VidyaModel, listener: VidyaListener) {
            binding.vidyaTitle.text = vidya.title
            binding.description.text = vidya.description
            binding.vidyaYear.text = vidya.year.toString()
            Picasso.get().load(vidya.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onVidyaClick(vidya) }
        }
    }
}
