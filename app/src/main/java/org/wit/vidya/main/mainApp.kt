package org.wit.vidya.main


import android.app.Application
import org.wit.vidya.models.VidyaJSONStore
import org.wit.vidya.models.VidyaModel
import timber.log.Timber
import timber.log.Timber.i
import org.wit.vidya.models.VidyaMemStore
import org.wit.vidya.models.VidyaStore

class MainApp : Application() {
    lateinit var games: VidyaStore
    //val games = ArrayList<VidyaModel>()
  //  val games = VidyaMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        games = VidyaJSONStore(applicationContext)
        i("Vidya started")
    }
}