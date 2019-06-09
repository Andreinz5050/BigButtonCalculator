package com.example.android.bigbuttoncalculator


import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.*
import android.os.Bundle

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.view.GravityCompat

import kotlinx.android.synthetic.main.activity_main.*

 abstract class MainActivity : AppCompatActivity() {

     protected var valueOne: String = ""
     protected var lastNumber: String = ""


     protected var lastOperation: String? = "="
     protected var resultLineSave = ""
     protected var operationLineSave = ""


    protected var menuAdapter: ArrayAdapter<String>? = null
    private var drawerToggle: ActionBarDrawerToggle? = null


     protected lateinit var menuAnimHamToCross: AnimatedVectorDrawable
     protected lateinit var menuAnimCrossToHam: AnimatedVectorDrawable
     protected lateinit  var startBtnMenu: VectorDrawable


     protected lateinit var finishBtnMenu: VectorDrawable


     protected var mMenuFlag = true

    protected var isThemChanged = false






    override fun onCreate(savedInstanceState: Bundle?) {





        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }



     protected fun addDrawerItems() {
        val osArray = arrayOf("Light", "Dark")
        menuAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, osArray)
        drawerList.adapter = menuAdapter

        drawerList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                when (position) {
                    0 -> {
                        isThemChanged = true

                        showLightTheme()
                        supportActionBar?.setHomeAsUpIndicator(menuAnimCrossToHam)

                        menuAnimCrossToHam.start()
                        drawerLayout.closeDrawer(GravityCompat.START, false)
                        operationLine.text = operationLineSave
                    }

                    1 -> {
                        isThemChanged = true
                        showDarkTheme()
                        supportActionBar?.setHomeAsUpIndicator(menuAnimCrossToHam)
                        menuAnimCrossToHam.start()
                        drawerLayout.closeDrawer(GravityCompat.START, false)
                        operationLine.text = operationLineSave
                    }
                }
            }
        }
    }

     protected fun showLightTheme() {
         val a = Intent(this,LightActivity::class.java)
         a.putExtra("RESULTLINE", resultLine!!.text)
         a.putExtra("OPERATIONLINE", operationLine!!.text)
         a.putExtra("OPERATION", lastOperation)
         a.putExtra("VALUEONE", valueOne)
         startActivity(a)
         finish()
     }
     protected fun showDarkTheme() {
         val a = Intent(this,DarkActivity::class.java)
         a.putExtra("RESULTLINE", resultLine!!.text)
         a.putExtra("OPERATIONLINE", operationLine!!.text)
         a.putExtra("OPERATION", lastOperation)
         a.putExtra("VALUEONE", valueOne)
         startActivity(a)
         finish()
     }
     protected fun setupDrawer() {
        drawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

                /** Called when a drawer has settled in a completely open state.  */
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                }

                /** Called when a drawer has settled in a completely closed state.  */
                override fun onDrawerClosed(view: View) {
                    super.onDrawerClosed(view)
                    invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                }
            }

        drawerToggle?.run {
            isDrawerIndicatorEnabled = true
            setHomeAsUpIndicator(R.drawable.start_btn_menu)
            drawerLayout.addDrawerListener(this)
        }
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle?.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            menuClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun menuClick() {
        if (mMenuFlag) {
            supportActionBar?.setHomeAsUpIndicator(menuAnimHamToCross)
            menuAnimHamToCross.start()
        } else {
            supportActionBar?.setHomeAsUpIndicator(menuAnimCrossToHam)
            menuAnimCrossToHam.start()
        }
        mMenuFlag = !mMenuFlag


    }



     protected  fun onChangeActivityExtract()
     {

         val extras = intent.extras
         if (extras != null ) {
             resultLine!!.text = extras.getString("RESULTLINE")
             operationLine!!.text = extras.getString("OPERATIONLINE")
             lastOperation = extras.getString("RESULTLINE")
             valueOne = extras.getString("VALUEONE")
         }

     }




    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("OPERATION")
        valueOne = savedInstanceState.getString("VALUEONE")

        resultLine!!.text = savedInstanceState.getString("RESULTLINE")
        operationLine!!.text = savedInstanceState.getString("OPERATIONLINE")
    }


    protected fun onNumberClick(view: View) {

        val button = view as Button

        val s = button.text.toString()
        valueOne += s
        lastNumber += s

        var valueOneWithoutLast = ""


        if (lastNumber.length > 1) {
            valueOneWithoutLast = lastNumber.substring(0, lastNumber.length - 1)
        }
        //var z = lastNumber.length
        if (lastNumber.length >= 2) {
            if (lastNumber[lastNumber.length - 1] == '.' && lastNumber[lastNumber.length - 2] == '.') {
                valueOne = valueOne.substring(0, valueOne.length - 1)
                lastNumber = lastNumber.substring(0, lastNumber.length - 1)
            } else if (valueOneWithoutLast.contains(".") && s == ".") {
                valueOne = valueOne.substring(0, valueOne.length - 1)
                lastNumber = lastNumber.substring(0, lastNumber.length - 1)
            }

        }


        operationLine!!.text = valueOne


        if (lastOperation == "=") {
            resultLine!!.text = ""
        } else if (lastOperation != "=") {


            try {
                performCalculation(valueOne)
            } catch (ex: Exception) {
                operationLine!!.text = ""
                resultLine!!.text = ""
            }

        } else {


            resultLine!!.text = ""

        }


    }


     protected fun onOperationClick(view: View) {

        val button = view as Button
        lastOperation = button.text.toString()
        lastNumber = ""




        if (lastOperation == "=" && valueOne == "") {

            resultLine!!.text = operationLine!!.text
            operationLine!!.text = ""


        } else if (lastOperation != "=" && valueOne == "") {

            operationLine!!.text = ""
        } else if (lastOperation != "=") {


            if (valueOne[valueOne.length - 1] == '×' || valueOne[valueOne.length - 1] == '/' || valueOne[valueOne.length - 1] == '+'
                || valueOne[valueOne.length - 1] == '-' || valueOne[valueOne.length - 1] == '%'
            ) {
                valueOne = valueOne.substring(0, valueOne.length - 1)
            }
            valueOne += lastOperation!!
            operationLine!!.text = valueOne

        } else if (lastOperation != "=" && operationLine.toString() == "") {


            operationLine!!.text = valueOne + lastOperation

            resultLine!!.text = ""

        } else if (lastOperation == "=") {


            valueOne = resultLine!!.text.toString()
            operationLine!!.text = ""


        }


    }


     protected fun backspaceClick(view: View) {

        var str = operationLine!!.text.toString()
        if (str != "") {
            str = str.substring(0, str.length - 1)
            operationLine!!.text = str


            try {
                performCalculation(str)
            } catch (ex: Exception) {
                operationLine!!.text = ""
                resultLine!!.text = ""
            }

        } else {
            lastOperation = "="
            operationLine!!.text = ""
            resultLine!!.text = ""
        }

        valueOne = str


    }

     protected fun resetClick(view: View) {

        valueOne = ""
        operationLine!!.text = ""
        resultLine!!.text = ""
        lastOperation = "="
    }


     private fun performCalculation(st: String) {
        var tempString = st
        var ifNoZero: String






        if (st[st.length - 1] == '×' || st[st.length - 1] == '/' || st[st.length - 1] == '+' || st[st.length - 1] == '-' || st[st.length - 1] == '%') {
            tempString = tempString.substring(0, tempString.length - 1)


            ifNoZero = Calculator.calculate(tempString)!!.toString()
            if (ifNoZero[ifNoZero.length - 1] == '0' && ifNoZero[ifNoZero.length - 2] == '.') {


                ifNoZero = ifNoZero.substring(0, ifNoZero.length - 1)
                resultLine!!.text = ifNoZero.substring(0, ifNoZero.length - 1)
            } else {
                resultLine!!.text = Calculator.calculate(tempString)!!.toString()
            }


        } else {


            ifNoZero = Calculator.calculate(st)!!.toString()
            if (ifNoZero[ifNoZero.length - 1] == '0' && ifNoZero[ifNoZero.length - 2] == '.') {
                ifNoZero = ifNoZero.substring(0, ifNoZero.length - 1)
                resultLine!!.text = ifNoZero.substring(0, ifNoZero.length - 1)
            } else {
                resultLine!!.text = Calculator.calculate(st)!!.toString()
            }


        }


    }


}
