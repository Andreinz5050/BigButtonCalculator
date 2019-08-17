package com.example.android.bigbuttoncalculator

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

class MenuFragment : Fragment() {

    companion object {
        const val IS_THEME_LIGHT = "ISLIGHT"
    }

    private lateinit var viewModel: MenuViewModel

    private lateinit var switchThemeButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.themes_menu, container, false)
        initializeFields(view)
        setUpListeners()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
    }

    private fun initializeFields(view: View) {
        switchThemeButton = view.findViewById(R.id.cooking_Switch_button)
        if (isLightTheme()) {
            switchThemeButton.setBackgroundResource(R.drawable.anim_btn_cooking_low_to_high)

            (switchThemeButton.background as AnimatedVectorDrawable).reset()

        } else {
            switchThemeButton.setBackgroundResource(R.drawable.anim_btn_cooking_high_to_low)

            (switchThemeButton.background as AnimatedVectorDrawable).reset()

        }
    }


    private fun setUpListeners() {
        switchThemeButton.setOnClickListener {
            (switchThemeButton.background as AnimatedVectorDrawable).start()
            if (isLightTheme()) {
                Handler().postDelayed({ this.findNavController().navigate(R.id.action_menuFragment_to_darkFragment) }, 800)
            } else {
                Handler().postDelayed({ this.findNavController().navigate(R.id.action_menuFragment_to_lightFragment) }, 800)
            }
        }
    }

    private fun isLightTheme(): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return true
        return sharedPref.getBoolean(IS_THEME_LIGHT, true)
    }
}