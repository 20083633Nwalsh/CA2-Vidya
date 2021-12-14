package org.wit.vidya.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.vidya.R
import org.wit.vidya.adapters.VidyaAdapter
import org.wit.vidya.adapters.VidyaListener
import org.wit.vidya.main.MainApp
import org.wit.vidya.models.VidyaModel
import org.wit.vidya.databinding.ActivityVidyaListBinding

class VidyaListActivity : AppCompatActivity(), VidyaListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityVidyaListBinding

    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVidyaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = VidyaAdapter(app.games)
        binding.recyclerView.adapter = VidyaAdapter(app.games.findAll(),this)

        registerRefreshCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        timber.log.Timber.i("menu triggered")
        when (item.itemId) {
            R.id.item_addgame -> {
                timber.log.Timber.i("clicked add")
                val launcherIntent = Intent(this, VidyaActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onVidyaClick(vidya: VidyaModel) {
        val launcherIntent = Intent(this, VidyaActivity::class.java)
        launcherIntent.putExtra("vidya_edit", vidya)
        refreshIntentLauncher.launch(launcherIntent)
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
*/
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}
