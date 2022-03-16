package org.wit.musicka.ui.song

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.wit.musicka.R
import org.wit.musicka.databinding.FragmentSongBinding
import org.wit.musicka.models.MusickaModel

class SongFragment : Fragment() {
        var musicka = MusickaModel()
       // var totalDonated = 0
        private var _fragBinding: FragmentSongBinding? = null
        // This property is only valid between onCreateView and onDestroyView.
        private val fragBinding get() = _fragBinding!!
        private lateinit var songViewModel: SongViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setHasOptionsMenu(true)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            _fragBinding = FragmentSongBinding.inflate(inflater, container, false)


            val root = fragBinding.root

            songViewModel = ViewModelProvider(this).get(SongViewModel::class.java)
            songViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                    status -> status?.let { render(status) }
            })

            fragBinding.musickaTitle.setText(musicka.title)
            fragBinding.description.setText(musicka.description)
            fragBinding.musickaYear.setText(musicka.year.toString())

            fragBinding.btnAdd.setText(R.string.save_musicka)
        //    Picasso.get()
         //       .load(musicka.image)
        //        .into(binding.musickaImage)
          //  if (musicka.image != Uri.EMPTY) {
         //       binding.chooseImage.setText(R.string.change_musicka_image)
         //   }


            setButtonListener(fragBinding)
            return root;
        }

        private fun render(status: Boolean) {
            when (status) {
                true -> {
                    view?.let {
                        //Uncomment this if you want to immediately return to Report
                        //findNavController().popBackStack()
                    }
                }
                false -> Toast.makeText(context,getString(R.string.musickaError),Toast.LENGTH_LONG).show()
            }
        }
/*
        fun setButtonListener(layout: FragmentSongBinding) {
            layout.musickaButton.setOnClickListener {
                val amount = if (layout.paymentAmount.text.isNotEmpty())
                    layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
                if(totalDonated >= layout.progressBar.max)
                    Toast.makeText(context,"Donate Amount Exceeded!", Toast.LENGTH_LONG).show()
                else {
                    val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                    totalDonated += amount
                    layout.totalSoFar.text = getString(R.string.total_donated,totalDonated)
                    layout.progressBar.progress = totalDonated

                }
            }
        }
*/
        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.menu_main, menu)
            super.onCreateOptionsMenu(menu, inflater)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return NavigationUI.onNavDestinationSelected(item,
                requireView().findNavController()) || super.onOptionsItemSelected(item)
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _fragBinding = null
        }
    }
