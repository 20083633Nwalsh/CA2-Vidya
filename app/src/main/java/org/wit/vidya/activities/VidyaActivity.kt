package org.wit.vidya.activities

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
import org.wit.vidya.R
import org.wit.vidya.databinding.ActivityVidyaBinding
import org.wit.vidya.helpers.showImagePicker
import org.wit.vidya.main.MainApp
import org.wit.vidya.models.Location
import org.wit.vidya.models.VidyaModel
import timber.log.Timber
import timber.log.Timber.i


class VidyaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVidyaBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var vidya = VidyaModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    //val games = ArrayList<VidyaModel>()

    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var edit = false

        binding = ActivityVidyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        // var app : MainApp? = null

        //Timber.plant(Timber.DebugTree())
        app = application as MainApp
        i("Vidya Activity started...")

        if (intent.hasExtra("vidya_edit")) {
            edit = true
            vidya = intent.extras?.getParcelable("vidya_edit")!!
            binding.vidyaTitle.setText(vidya.title)
            binding.description.setText(vidya.description)
            binding.btnAdd.setText(R.string.save_vidya)
            Picasso.get()
                .load(vidya.image)
                .into(binding.vidyaImage)
            if (vidya.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_vidya_image)
            }

        }

        binding.btnAdd.setOnClickListener() {
            vidya.title = binding.vidyaTitle.text.toString()
            vidya.description = binding.description.text.toString()
            if (vidya.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_vidya_title, Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                if (edit) {
                    app.games.update(vidya.copy())
                } else {
                    app.games.create(vidya.copy())
                }
            }
            i("add Button pressed: $vidya")
            setResult(RESULT_OK)
            finish()
            }

        binding.chooseImage.setOnClickListener {
            i("Select image")
            showImagePicker(imageIntentLauncher)
        }

        binding.vidyaLocation.setOnClickListener {
            i ("Set Location Pressed")
        }

        binding.vidyaLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
        }
/*
        binding.vidyaLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
*/
        binding.vidyaLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            vidya.image = result.data!!.data!!
                            Picasso.get()
                                .load(vidya.image)
                                .into(binding.vidyaImage)
                            binding.chooseImage.setText(R.string.change_vidya_image)
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
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}
