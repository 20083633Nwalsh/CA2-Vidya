package org.wit.musicka.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.musicka.R
import org.wit.musicka.adapter.MusickaAdapter
import org.wit.musicka.databinding.FragmentHomeBinding
import org.wit.musicka.main.MusickaApp

class HomeFragment : Fragment() {
    lateinit var app: MusickaApp

    private var _fragBinding: FragmentHomeBinding? = null
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
        _fragBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = "Home"

        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = MusickaAdapter(app.musickaStore.findAll())
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}