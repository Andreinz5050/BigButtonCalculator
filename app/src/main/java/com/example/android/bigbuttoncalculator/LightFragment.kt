package com.example.android.bigbuttoncalculator

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable


class LightFragment : BaseThemeView() {



    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



     companion object {
        fun newInstance() = LightFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        menuAnimHamToCross = getDrawable(R.drawable.anim_btn_menu_ham_to_cross) as AnimatedVectorDrawable
        menuAnimCrossToHam = getDrawable(R.drawable.anim_btn_menu_cross_to_ham) as AnimatedVectorDrawable
        startBtnMenu = getDrawable(R.drawable.start_btn_menu) as VectorDrawable
        finishBtnMenu = getDrawable(R.drawable.finish_btn_menu) as VectorDrawable

        addDrawerItems()
        setupDrawer()

        supportActionBar?.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(startBtnMenu)
        }

        onChangeActivityExtract()


    }




    }



