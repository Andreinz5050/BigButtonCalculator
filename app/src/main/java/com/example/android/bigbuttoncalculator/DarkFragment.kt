package com.example.android.bigbuttoncalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DarkFragment : BaseThemeFragment(){


    companion object {
        fun newInstance() = DarkFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dark_fragment_layout, container, false)
        initializeFields(view)
        setUp()
        passTheme()




        return view
    }

     fun passTheme()
    {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("ISLIGHT","false")
            apply()
        }

    }
}