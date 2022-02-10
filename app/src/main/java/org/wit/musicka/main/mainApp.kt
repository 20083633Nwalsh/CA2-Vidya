package org.wit.musicka.main


import android.app.Application
import org.wit.musicka.models.MusickaJSONStore
import timber.log.Timber
import timber.log.Timber.i
import org.wit.musicka.models.MusickaStore

class MainApp : Application() {
    lateinit var songs: MusickaStore
    //val songs = ArrayList<MusickaModel>()
  //  val songs = MusickaMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        songs = MusickaJSONStore(applicationContext)
        i("Musicka started")
    }
}