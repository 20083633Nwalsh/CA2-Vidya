package org.wit.vidya.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class VidyaMemStore : VidyaStore {

    val games = ArrayList<VidyaModel>()

    override fun findAll(): List<VidyaModel> {
        return games
    }

    override fun create(vidya: VidyaModel) {
        vidya.id = getId()
        games.add(vidya)
        logAll()
    }

    override fun update(vidya: VidyaModel) {
        var foundVidya: VidyaModel? = games.find { p -> p.id == vidya.id }
        if (foundVidya != null) {
            foundVidya.title = vidya.title
            foundVidya.description = vidya.description
            foundVidya.image = vidya.image
            logAll()
        }
    }

    fun logAll() {
        games.forEach{ i("${it}") }
    }
}