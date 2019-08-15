package com.example.android.bigbuttoncalculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LightFragment : BaseThemeFragment() {

    companion object {
        fun newInstance() = LightFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.light_fragment_layout, container, false)
        initializeFields(view)
        setUp()
        passTheme()



        return view
    }

    private fun passTheme() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean("ISLIGHT", true)
            apply()
        }

    }
}