package org.wit.vidya.main


import android.app.Application
import org.wit.vidya.models.VidyaModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val games = ArrayList<VidyaModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Vidya started")
    }
}