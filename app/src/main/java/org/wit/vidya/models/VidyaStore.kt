package org.wit.vidya.models

interface VidyaStore {
    fun findAll(): List<VidyaModel>
    fun create(vidya: VidyaModel)
    fun update(vidya: VidyaModel)
    fun delete(vidya: VidyaModel)
}