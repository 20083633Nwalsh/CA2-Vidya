package org.wit.vidya.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.vidya.R
import org.wit.vidya.adapters.VidyaAdapter
import org.wit.vidya.adapters.VidyaListener
import org.wit.vidya.databinding.ActivityVidyaListBinding
import org.wit.vidya.main.MainApp
import org.wit.vidya.models.VidyaModel


class VidyaListActivity : AppCompatActivity(), VidyaListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityVidyaListBinding

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

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, VidyaActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onVidyaClick(vidya: VidyaModel) {
        val launcherIntent = Intent(this, VidyaActivity::class.java)
        launcherIntent.putExtra("vidya_edit", vidya)
        startActivityForResult(launcherIntent,0)
    }
}
