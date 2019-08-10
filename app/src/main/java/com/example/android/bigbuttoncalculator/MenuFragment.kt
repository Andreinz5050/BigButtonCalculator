package com.example.android.bigbuttoncalculator

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
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

    private var isLoadingThemeLight = true


    lateinit var switchThemeButton: Button

    var count = 0

    private lateinit var lightThemeFinish: VectorDrawable
    private lateinit var darkThemeFinish: VectorDrawable
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




        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        isLoadingThemeLight = sharedPref.getString("ISLIGHT","true").toBoolean()

        switchThemeButton = view.findViewById<Button>(R.id.cooking_Switch_button).apply {


               if (isLoadingThemeLight) {

                   setBackgroundResource(R.drawable.anim_btn_cooking_low_to_high)
                   darkThemeAnim = background as AnimatedVectorDrawable


               } else {


                   setBackgroundResource(R.drawable.anim_btn_cooking_high_to_low)
                   lightThemeAnim = background as AnimatedVectorDrawable

               }

           }



    }


    fun setUp() {
        switchThemeButton.setOnClickListener(this)



    }

    override fun onClick(view: View?) {

        when (view) {
            switchThemeButton -> {
                if (isLoadingThemeLight) {
                    switchThemeButton.setBackground(darkThemeAnim)
                    darkThemeAnim.start()
                    Handler().postDelayed({
                        //switchThemeButton.setBackground(darkThemeFinish)
                        this.findNavController().navigate(R.id.darkFragment)

                    }, 800)

                } else {
                    switchThemeButton.setBackground(lightThemeAnim)
                    lightThemeAnim.start()
                    Handler().postDelayed({
                        //switchThemeButton.setBackground(lightThemeFinish)
                        this.findNavController().navigate(R.id.lightFragment)
                        //CHANGED
                    }, 800)

                }


                /* when (view) {
            switchThemeButton -> {




            }



        }*/

            }
        }
    }
}