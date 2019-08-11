package com.example.android.bigbuttoncalculator

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

class MenuFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: MenuViewModel

    private var isLoadingThemeLight = true

    private lateinit var switchThemeButton: Button

    private lateinit var themeAnim: AnimatedVectorDrawable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.themes_menu, container, false)
        initializeFields(view)
        switchThemeButton.setOnClickListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
    }

    private fun initializeFields(view: View) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        isLoadingThemeLight = sharedPref.getBoolean("ISLIGHT", true)
        switchThemeButton = view.findViewById<Button>(R.id.cooking_Switch_button).apply {
            themeAnim = if (isLoadingThemeLight) {
                setBackgroundResource(R.drawable.anim_btn_cooking_low_to_high)
                background as AnimatedVectorDrawable
            } else {
                setBackgroundResource(R.drawable.anim_btn_cooking_high_to_low)
                background as AnimatedVectorDrawable
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            switchThemeButton -> {
                switchThemeButton.background = themeAnim
                themeAnim.start()
                if (isLoadingThemeLight) {
                    Handler().postDelayed({ this.findNavController().navigate(R.id.darkFragment) }, 800)
                } else {
                    Handler().postDelayed({ this.findNavController().navigate(R.id.lightFragment) }, 800)
                }
            }
        }
    }
}