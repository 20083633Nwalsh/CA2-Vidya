package org.wit.vidya.main


import android.app.Application
import org.wit.vidya.models.VidyaModel
import timber.log.Timber
import timber.log.Timber.i
import org.wit.vidya.models.VidyaMemStore

class MainApp : Application() {

    //val games = ArrayList<VidyaModel>()
    val games = VidyaMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Vidya started")
    }
}