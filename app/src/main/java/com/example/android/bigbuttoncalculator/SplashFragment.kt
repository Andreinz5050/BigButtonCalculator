package com.example.android.bigbuttoncalculator

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.splash_screen.*


class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.splash_screen, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (animatedImage.drawable as Animatable).start()
        Handler().postDelayed({
            context?.let {
                findNavController().navigate(R.id.action_splashFragment_to_lightFragment)
            }
        }, 8000)
    }




}