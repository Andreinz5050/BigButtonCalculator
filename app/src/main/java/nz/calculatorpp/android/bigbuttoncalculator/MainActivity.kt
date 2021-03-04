package nz.calculatorpp.android.bigbuttoncalculator


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector

import dagger.android.support.HasSupportFragmentInjector
import androidx.fragment.app.Fragment


import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = null
        supportActionBar?.hide()

    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector


}