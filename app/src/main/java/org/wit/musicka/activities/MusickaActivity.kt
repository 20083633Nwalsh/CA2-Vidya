package org.wit.musicka.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.musicka.R
import org.wit.musicka.databinding.ActivityMusickaBinding
import org.wit.musicka.helpers.showImagePicker
import org.wit.musicka.main.MainApp
import org.wit.musicka.models.Location
import org.wit.musicka.models.MusickaModel
import timber.log.Timber.i


class MusickaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusickaBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var musicka = MusickaModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    //val songs = ArrayList<MusickaModel>()

   // var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var edit = false

        binding = ActivityMusickaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Musicka Activity started...")

        if (intent.hasExtra("musicka_edit")) {
            edit = true
            musicka = intent.extras?.getParcelable("musicka_edit")!!
            binding.musickaTitle.setText(musicka.title)
            binding.description.setText(musicka.description)

            binding.musickaYear.setText(musicka.year.toString())

            binding.btnAdd.setText(R.string.save_musicka)
            Picasso.get()
                .load(musicka.image)
                .into(binding.musickaImage)
            if (musicka.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_musicka_image)
            }

        }

        binding.btnAdd.setOnClickListener() {
            musicka.title = binding.musickaTitle.text.toString()
            musicka.description = binding.description.text.toString()

            musicka.year = binding.musickaYear.text.toString().toInt()

            if (musicka.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_musicka_title, Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                if (edit) {
                    app.songs.update(musicka.copy())
                } else {
                    app.songs.create(musicka.copy())
                }
            }
            i("add Button pressed: $musicka")
            setResult(RESULT_OK)
            finish()
            }

        binding.chooseImage.setOnClickListener {
            i("Select image")
            showImagePicker(imageIntentLauncher)
        }

        binding.musickaLocation.setOnClickListener {
            i ("Set Location Pressed")
        }

        binding.musickaLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.musickaLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (musicka.zoom != 0f) {
                location.lat =  musicka.lat
                location.lng = musicka.lng
                location.zoom = musicka.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }



        registerImagePickerCallback()
        registerMapCallback()
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_musicka, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.songs.delete(musicka)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            musicka.image = result.data!!.data!!
                            Picasso.get()
                                .load(musicka.image)
                                .into(binding.musickaImage)
                            binding.chooseImage.setText(R.string.change_musicka_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            musicka.lat = location.lat
                            musicka.lng = location.lng
                            musicka.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


}
