package com.example.android.bigbuttoncalculator

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView

class SplashActivity : Activity() {
    //internal var vectorAnimation: AnimationDrawable? = null
    // Время в милесекундах, в течение которого будет отображаться Splash Screen
    private val SPLASH_DISPLAY_LENGTH = 2000


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val imageView = findViewById(R.id.image) as ImageView

        (imageView.drawable as Animatable).start()



        Handler().postDelayed({
            // По истечении времени, запускаем главный активити, а Splash Screen закрываем
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }


}