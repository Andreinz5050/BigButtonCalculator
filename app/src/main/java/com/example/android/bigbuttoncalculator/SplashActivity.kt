package com.example.android.bigbuttoncalculator

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.splash_screen.*

class SplashActivity : Activity() {
    //internal var vectorAnimation: AnimationDrawable? = null
    // Время в милесекундах, в течение которого будет отображаться Splash Screen
    private val SPLASH_DISPLAY_LENGTH = 2000L


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        (animatedImage.drawable as Animatable).start()

        // По истечении времени, запускаем главный активити, а Splash Screen закрываем
        Handler().postDelayed({
            showMainScreen()
        }, SPLASH_DISPLAY_LENGTH)
    }

    private fun showMainScreen() {
        startActivity(Intent(this, LightActivity::class.java) )
        finish()
    }


}