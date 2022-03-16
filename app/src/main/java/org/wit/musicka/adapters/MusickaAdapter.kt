package org.wit.musicka.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.musicka.R
import org.wit.musicka.databinding.CardMusickaBinding
import org.wit.musicka.models.MusickaModel
import org.wit.musicka.ui.musickaList.MusickaFragment

interface MusickaClickListener {
    fun onMusickaClick(song: MusickaModel)
}

class MusickaAdapter constructor(private var songs: List<MusickaModel>,
                                  private val listener: MusickaClickListener)
    : RecyclerView.Adapter<MusickaAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardMusickaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val song = songs[holder.adapterPosition]
        holder.bind(song,listener)
    }

    override fun getItemCount(): Int = songs.size

    inner class MainHolder(val binding : CardMusickaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: MusickaModel, listener: MusickaClickListener) {
            binding.song = song
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onMusickaClick(song) }
            binding.executePendingBindings()
        }
    }
}