package org.wit.musicka.ui.musickaList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.wit.musicka.R
import org.wit.musicka.adapters.MusickaAdapter
import org.wit.musicka.adapters.MusickaClickListener
import org.wit.musicka.databinding.FragmentMusickaBinding
import org.wit.musicka.main.MainApp
import org.wit.musicka.models.MusickaManager.songs
import org.wit.musicka.models.MusickaModel

class MusickaFragment : Fragment(), MusickaClickListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentMusickaBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var musickaViewModel: MusickaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMusickaBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        musickaViewModel = ViewModelProvider(this).get(MusickaViewModel::class.java)
        musickaViewModel.observableSongsList.observe(viewLifecycleOwner, Observer {
                songs ->
            songs?.let { render(songs) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = MusickaFragmentDirections.actionNavMusickaToNavSong()
            findNavController().navigate(action)
        }
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(songsList: List<MusickaModel>) {
        fragBinding.recyclerView.adapter = MusickaAdapter(songsList,this)
        if (songsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.donationsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.donationsNotFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        val musickaViewModel = ViewModelProvider(this).get(MusickaViewModel::class.java)
        reportViewModel.observableSongsList.observe(viewLifecycleOwner, Observer {
            totalDonated = reportViewModel.observableDonationsList.value!!.sumOf { it.amount }
            fragBinding.progressBar.progress = totalDonated
            fragBinding.totalSoFar.text = getString(R.string.total_donated,totalDonated)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onMusickaClick(song: MusickaModel) {
        val action = MusickaFragmentDirections.actionNavMusickaToMusickaDetailFragment()
        findNavController().navigate(action)
    }
}