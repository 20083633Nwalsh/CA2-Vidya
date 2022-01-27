package org.wit.musicka.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MusickaMemStore : MusickaStore {

    val games = ArrayList<MusickaModel>()

    override fun findAll(): List<MusickaModel> {
        return games
    }

    override fun create(musicka: MusickaModel) {
        musicka.id = getId()
        games.add(musicka)
        logAll()
    }

    override fun update(musicka: MusickaModel) {
        var foundMusicka: MusickaModel? = games.find { p -> p.id == musicka.id }
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
        games.remove(musicka)
    }

    fun logAll() {
        games.forEach{ i("${it}") }
    }
}