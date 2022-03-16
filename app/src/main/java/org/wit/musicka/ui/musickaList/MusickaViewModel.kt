package org.wit.musicka.ui.musickaList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.musicka.models.MusickaManager
import org.wit.musicka.models.MusickaModel

class MusickaViewModel : ViewModel() {

    private val songsList = MutableLiveData<List<MusickaModel>>()

    val observableSongsList: LiveData<List<MusickaModel>>
        get() = songsList

    init {
        load()
    }

    fun load() {
        songsList.value = MusickaManager.findAll()
    }
}