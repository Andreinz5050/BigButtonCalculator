package com.example.android.bigbuttoncalculator



import android.os.Bundle
import android.view.View
import android.view.Window

import androidx.appcompat.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector

import dagger.android.support.HasSupportFragmentInjector
import androidx.fragment.app.Fragment


import javax.inject.Inject



class MainActivity : AppCompatActivity(), HasSupportFragmentInjector  {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(null)
        getSupportActionBar()?.hide()

    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector



}
