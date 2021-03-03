package nz.calculatorpp.android.popcorn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class DarkFragment : BaseThemeFragment() {


    companion object {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dark_fragment_layout, container, false)
        initializeFields(view)
        setUp()
        menuButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_darkFragment_to_menuFragment)
        }
        passTheme()




        return view
    }

    private fun passTheme() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean("ISLIGHT", false)
            apply()
        }

    }
}