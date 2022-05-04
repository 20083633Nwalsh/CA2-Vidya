package org.wit.musicka.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.musicka.R
import org.wit.musicka.databinding.CardMusickaBinding
import org.wit.musicka.models.MusickaModel

class MusickaAdapter constructor(private var songs: List<MusickaModel>)
    : RecyclerView.Adapter<MusickaAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardMusickaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val song = songs[holder.adapterPosition]
        holder.bind(song)
    }

    override fun getItemCount(): Int = songs.size

    inner class MainHolder(val binding : CardMusickaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: MusickaModel) {
            binding.musickaTitle.text = song.title
            binding.description.text = song.description
            binding.musickaYear.text = song.year.toString()
           // binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)


          //  Picasso.get().load(musicka.image).resize(200,200).into(binding.imageIcon)

        }
    }
}