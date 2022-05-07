package org.wit.musicka.models

import androidx.recyclerview.widget.RecyclerView





interface MusickaStore {
    fun findAll(): List<MusickaModel>
    fun create(musicka: MusickaModel)
    fun update(musicka: MusickaModel)
    fun delete(musicka: MusickaModel)
}

internal interface RecyclerItemTouchHelperListener {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int)
}