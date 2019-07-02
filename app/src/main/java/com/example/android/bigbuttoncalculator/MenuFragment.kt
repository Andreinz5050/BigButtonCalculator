package com.example.android.bigbuttoncalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class MenuFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var viewModel: MenuViewModel


    private lateinit var lightThemeButton: ImageView
    private lateinit var darkThemeButton: ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.themes_menu, container, false)
        initializeFields(view)
        setUp()


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MenuViewModel::class.java)

    }


    fun initializeFields(view: View) {


        lightThemeButton = view.findViewById(R.id.light_theme_button)
        darkThemeButton = view.findViewById(R.id.dark_theme_button)


    }


    fun setUp() {
        lightThemeButton.setOnClickListener(this)
        darkThemeButton.setOnClickListener(this)


    }

    override fun onClick(view: View?) {

        when (view) {
            lightThemeButton -> this.findNavController().navigate(R.id.lightFragment)
            darkThemeButton -> this.findNavController().navigate(R.id.darkFragment)


        }


    }
}