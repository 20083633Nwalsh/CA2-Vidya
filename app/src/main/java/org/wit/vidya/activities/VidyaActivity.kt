package org.wit.vidya.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.vidya.R
import org.wit.vidya.databinding.ActivityVidyaBinding
import org.wit.vidya.main.MainApp
import org.wit.vidya.models.VidyaModel
import timber.log.Timber
import timber.log.Timber.i


class VidyaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVidyaBinding
    var vidya = VidyaModel()
    lateinit var app: MainApp
    //val games = ArrayList<VidyaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVidyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        var app : MainApp? = null

        //Timber.plant(Timber.DebugTree())
        app = application as MainApp
        i("Vidya Activity started...")

        if (intent.hasExtra("vidya_edit")) {
            vidya = intent.extras?.getParcelable("vidya_edit")!!
            binding.vidyaTitle.setText(vidya.title)
            binding.description.setText(vidya.description)
        }

        binding.btnAdd.setOnClickListener() {
            vidya.title = binding.vidyaTitle.text.toString()
            vidya.description = binding.description.text.toString()
            if (vidya.title.isNotEmpty()) {
                app.games.create(vidya.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_vidya, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

//PlacemarkList