package org.wit.musicka.ui.add

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.wit.musicka.R
import org.wit.musicka.databinding.FragmentAddBinding
import org.wit.musicka.main.MusickaApp
import org.wit.musicka.models.MusickaModel


class AddFragment : Fragment() {
    lateinit var app: MusickaApp

    //   var totalDonated = 0
    private var _fragBinding: FragmentAddBinding? = null
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

        _fragBinding = FragmentAddBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_add)

        fragBinding.musickaTitle.text.toString()
        fragBinding.description.text.toString()
        fragBinding.musickaYear.toString()


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

    fun setButtonListener(layout: FragmentAddBinding) {

        layout.btnAdd.setOnClickListener {
            val title = layout.musickaTitle.text.toString()
            val description = if (layout.description.text.isEmpty())
                "No Description" else layout.description.text.toString()
            val year = layout.musickaYear.text.toString().toInt()


            app.musickaStore.create(MusickaModel(title = title, year = year, description = description))


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

}
     /*   layout.donateButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                Toast.makeText(context,"Donate Amount Exceeded!",Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                layout.totalSoFar.text = "$$totalDonated"
                layout.progressBar.progress = totalDonated
                app.donationsStore.create(DonationModel(paymentmethod = paymentmethod,amount = amount))
            }
        }

    }


}
*/
