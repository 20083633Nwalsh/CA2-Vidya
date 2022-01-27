package org.wit.musicka.main


import android.app.Application
import org.wit.musicka.models.MusickaJSONStore
import timber.log.Timber
import timber.log.Timber.i
import org.wit.musicka.models.MusickaStore

class MainApp : Application() {
    lateinit var games: MusickaStore
    //val games = ArrayList<MusickaModel>()
  //  val games = MusickaMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        games = MusickaJSONStore(applicationContext)
        i("Musicka started")
    }
}