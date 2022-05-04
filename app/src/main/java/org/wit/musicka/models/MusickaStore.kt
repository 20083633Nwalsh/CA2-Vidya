package org.wit.musicka.models


interface MusickaStore {
    fun findAll(): List<MusickaModel>
    fun create(musicka: MusickaModel)
    fun update(musicka: MusickaModel)
    fun delete(musicka: MusickaModel)
}