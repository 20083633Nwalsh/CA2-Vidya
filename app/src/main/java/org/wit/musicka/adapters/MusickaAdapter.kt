package org.wit.musicka.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.musicka.databinding.CardMusickaBinding
import org.wit.musicka.models.MusickaModel

interface MusickaListener {
    fun onMusickaClick(musicka: MusickaModel)
}

class MusickaAdapter constructor(private var songs: List<MusickaModel>,
                                   private val listener: MusickaListener) :
    RecyclerView.Adapter<MusickaAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardMusickaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val musicka = songs[holder.adapterPosition]
        holder.bind(musicka, listener)
    }

    override fun getItemCount(): Int = songs.size

    class MainHolder(private val binding : CardMusickaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(musicka: MusickaModel, listener: MusickaListener) {
            binding.musickaTitle.text = musicka.title
            binding.description.text = musicka.description
            binding.musickaYear.text = musicka.year.toString()
            Picasso.get().load(musicka.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onMusickaClick(musicka) }
        }
    }
}
