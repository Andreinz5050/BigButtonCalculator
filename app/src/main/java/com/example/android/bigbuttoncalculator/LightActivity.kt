package com.example.android.bigbuttoncalculator

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*


class LightActivity : MainActivity() {


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



