package org.wit.musicka.ui.song

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.musicka.models.MusickaManager
import org.wit.musicka.models.MusickaModel

class SongViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addSong(song: MusickaModel) {
        status.value = try {
            MusickaManager.create(song)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}