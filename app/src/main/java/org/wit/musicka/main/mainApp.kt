package org.wit.musicka.main

import android.app.Application
import org.wit.musicka.models.MusickaStore
import timber.log.Timber


class MainApp : Application() {
    lateinit var songs: MusickaStore
    //val songs = ArrayList<MusickaModel>()
    //  val songs = MusickaMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //  musickaStore = MusickaManager()
       // songs = MusickaJSONStore(applicationContext)
        Timber.i("Musicka started")
    }
}