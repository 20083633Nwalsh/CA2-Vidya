package org.wit.musicka.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MusickaMemStore : MusickaStore {

    val songs = ArrayList<MusickaModel>()

    override fun findAll(): List<MusickaModel> {
        return songs
    }

    override fun create(musicka: MusickaModel) {
        musicka.id = getId()
        songs.add(musicka)
        logAll()
    }

    override fun update(musicka: MusickaModel) {
        var foundMusicka: MusickaModel? = songs.find { p -> p.id == musicka.id }
        if (foundMusicka != null) {
            foundMusicka.title = musicka.title
            foundMusicka.description = musicka.description
            foundMusicka.year = musicka.year
            foundMusicka.image = musicka.image
            foundMusicka.lat = musicka.lat
            foundMusicka.lng = musicka.lng
            foundMusicka.zoom = musicka.zoom
            logAll()
        }
    }

    override fun delete(musicka: MusickaModel) {
        songs.remove(musicka)
    }

    fun logAll() {
        songs.forEach{ i("${it}") }
    }
}