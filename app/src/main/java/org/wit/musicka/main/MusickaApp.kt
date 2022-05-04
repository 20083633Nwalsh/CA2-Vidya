package org.wit.musicka.main

import android.app.Application
import org.wit.musicka.models.MusickaMemStore
import org.wit.musicka.models.MusickaStore

import timber.log.Timber

class MusickaApp : Application() {

    lateinit var musickaStore: MusickaStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        musickaStore = MusickaMemStore()
        Timber.i("Musicka Application Started")
    }
}