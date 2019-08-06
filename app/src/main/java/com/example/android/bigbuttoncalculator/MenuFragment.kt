package com.example.android.bigbuttoncalculator

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class MenuFragment : Fragment(), View.OnClickListener {




    private lateinit var viewModel: MenuViewModel


  lateinit var switchThemeButton: Switch


    private lateinit var lightThemeAnim: AnimatedVectorDrawable
    private lateinit var darkThemeAnim: AnimatedVectorDrawable



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.themes_menu, container, false)
        initializeFields(view)
        setUp()


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)

    }


    private fun initializeFields(view: View) {



        switchThemeButton = view.findViewById<Switch>(R.id.cooking_Switch_button).apply {
            if (isChecked) {
                setBackgroundResource(R.drawable.anim_btn_cooking_high_to_low)
                lightThemeAnim = background as AnimatedVectorDrawable



            } else {

                setBackgroundResource(R.drawable.anim_btn_cooking_low_to_high)
                darkThemeAnim = background as AnimatedVectorDrawable

            }






        }


    }


    fun setUp() {
        switchThemeButton.setOnClickListener(this)
        //darkThemeButton.setOnClickListener(this)


    }

     override fun onClick(view: View?) {

         switchThemeButton.setOnCheckedChangeListener { _, isChecked ->
             if (isChecked)
             {
                 switchThemeButton.setBackground(lightThemeAnim)
                 lightThemeAnim.start()
                 Handler().postDelayed({

                     this.findNavController().navigate(R.id.lightFragment)

                 }, 700)

             }
             else
             {
                 switchThemeButton.setBackground(darkThemeAnim)
                 darkThemeAnim.start()
                 Handler().postDelayed({

                     this.findNavController().navigate(R.id.darkFragment)

                 }, 700)

             }
         }

       /* when (view) {
            switchThemeButton -> {




            }



        }*/


    }
}