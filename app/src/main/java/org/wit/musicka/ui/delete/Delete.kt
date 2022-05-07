package org.wit.musicka.ui.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.musicka.R
import org.wit.musicka.databinding.FragmentAddBinding
import org.wit.musicka.databinding.FragmentDeleteBinding
import org.wit.musicka.main.MusickaApp
import org.wit.musicka.models.MusickaJSONStore
import org.wit.musicka.models.MusickaModel
import org.wit.musicka.ui.add.AddFragment
import timber.log.Timber
import java.nio.file.Files.delete

class Delete: Fragment()  {
    lateinit var app: MusickaApp

    //   var totalDonated = 0
    private var _fragBinding: FragmentDeleteBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MusickaApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentDeleteBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_add)
/*
        fragBinding.musickaTitle.text.toString()
        fragBinding.description.text.toString()
        fragBinding.musickaYear.toString()
*/

        setButtonListener(fragBinding)
        return root;

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        //  totalDonated = app.donationsStore.findAll().sumOf { it.amount }
        //  fragBinding.progressBar.progress = totalDonated
        //  fragBinding.totalSoFar.text = "$$totalDonated"
    }

    fun setButtonListener(layout: FragmentDeleteBinding) {

        layout.buttonDelete.setOnClickListener {
         val title = layout.inputTitle.text.toString()
            app.musickaStore.delete(MusickaModel(title = title))
            Timber.plant(Timber.DebugTree())
            Timber.i("Delete Button Pressed")
            Timber.i(title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

}