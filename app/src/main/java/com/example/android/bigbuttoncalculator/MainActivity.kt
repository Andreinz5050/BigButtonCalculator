package com.example.android.bigbuttoncalculator


import android.content.res.Configuration
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    internal var operationLine: TextView? = null
    internal var resultLine: TextView? = null

    internal var valueOne: String? = ""
    internal var lastNumber: String? = ""


    internal var lastOperation: String? = "="
    internal var resultLineSave = ""
    internal var operationLineSave = ""


    private var mDrawerList: ListView? = null
    private var mAdapter: ArrayAdapter<String>? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null


    private val menuAnimHamToCross: Animatable? = R.drawable.anim_btn_menu_ham_to_cross as Animatable
    internal var myToolbar: Toolbar? = null
    private val menuAnimCrossToHam: Animatable ? = R.drawable.anim_btn_menu_cross_to_ham as Animatable

    private var mMenuFlag = true


    override fun onCreate(savedInstanceState: Bundle?) {


        setTheme(R.style.FeedActivityThemeLight)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        operationLine = operation_line as TextView
        resultLine = result_line as TextView

        myToolbar = toolbar as Toolbar
        myToolbar!!.setNavigationIcon(R.drawable.start_btn_menu)
//        setSupportActionBar(myToolbar)


        mDrawerLayout = drawer_layout as DrawerLayout

       // menuAnimHamToCross = R.drawable.anim_btn_menu_ham_to_cross as AnimatedVectorDrawable
        //menuAnimCrossToHam = R.drawable.anim_btn_menu_cross_to_ham as AnimatedVectorDrawable


        mDrawerList = navList as ListView




        addDrawerItems()
        setupDrawer()

        if (supportActionBar != null) {
            supportActionBar!!.setTitle("") // or whatever you want to use
        }


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.start_btn_menu)


    }


    private fun addDrawerItems() {
        val osArray = arrayOf("Light", "Dark")
        mAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, osArray)
        mDrawerList!!.adapter = mAdapter

        mDrawerList!!.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                when (position) {
                    0 -> {
                        run {
                            setTheme(R.style.FeedActivityThemeLight)
                            this@MainActivity.recreate()
                            supportActionBar!!.setHomeAsUpIndicator(menuAnimCrossToHam)
                            menuAnimCrossToHam!!.start()
                            mDrawerLayout!!.closeDrawer(Gravity.START, false)
                            operationLine!!.text = operationLineSave

                        }
                        run {
                            setTheme(R.style.FeedActivityThemeDark)
                            this@MainActivity.recreate()
                            supportActionBar!!.setHomeAsUpIndicator(menuAnimCrossToHam)
                            menuAnimCrossToHam!!.start()
                            mDrawerLayout!!.closeDrawer(Gravity.START, false)
                            operationLine!!.text = operationLineSave

                        }
                    }

                    1 -> run {
                        setTheme(R.style.FeedActivityThemeDark)
                        this@MainActivity.recreate()
                        supportActionBar!!.setHomeAsUpIndicator(menuAnimCrossToHam)
                        menuAnimCrossToHam!!.start()
                        mDrawerLayout!!.closeDrawer(Gravity.START, false)
                        operationLine!!.text = operationLineSave
                    }
                }
            }
        }
    }

    private fun setupDrawer() {
        mDrawerToggle =
            object : ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

                /** Called when a drawer has settled in a completely open state.  */
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    // getSupportActionBar().setTitle("Navigation!");
                    invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                }

                /** Called when a drawer has settled in a completely closed state.  */
                override fun onDrawerClosed(view: View) {
                    super.onDrawerClosed(view)
                    //getSupportActionBar().setTitle(mActivityTitle);
                    invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                }
            }

        mDrawerToggle!!.isDrawerIndicatorEnabled = true
        mDrawerToggle!!.setHomeAsUpIndicator(R.drawable.start_btn_menu)
        mDrawerLayout!!.addDrawerListener(mDrawerToggle!!)


    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mDrawerToggle!!.onOptionsItemSelected(item)) {
            menuClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun menuClick() {
        if (mMenuFlag) {
            supportActionBar!!.setHomeAsUpIndicator(menuAnimHamToCross)
            menuAnimHamToCross!!.start()
        } else {
            supportActionBar!!.setHomeAsUpIndicator(menuAnimCrossToHam)
            menuAnimCrossToHam!!.start()
        }
        mMenuFlag = !mMenuFlag


    }


    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("OPERATION", lastOperation)
        if (resultLineSave != "")
            outState.putString("RESULTLINE", resultLineSave)
        if (operationLineSave != "")
            outState.putString("OPERATIONLINE", operationLineSave)
        if (valueOne != "")
            outState.putString("VALUEONE", valueOne)



        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("OPERATION")
        valueOne = savedInstanceState.getString("VALUEONE")

        resultLine!!.text = savedInstanceState.getString("RESULTLINE")
        operationLine!!.text = savedInstanceState.getString("OPERATIONLINE")
    }


    fun onNumberClick(view: View) {

        val button = view as Button

        val s = button.text.toString()
        valueOne = valueOne!! + s
        lastNumber + s
        var valueOneWithoutLast = lastNumber!!.substring(0, lastNumber!!.length - 1)
        if (lastNumber!!.length >= 2) {
            if (lastNumber!![lastNumber!!.length - 1] == '.' && lastNumber!![lastNumber!!.length - 2] == '.') {
                valueOne = valueOne!!.substring(0, valueOne!!.length - 1)
                lastNumber = lastNumber!!.substring(0, lastNumber!!.length - 1)
            } else if (valueOneWithoutLast.contains(".") && s == ".") {
                valueOne = valueOne!!.substring(0, valueOne!!.length - 1)
                lastNumber = lastNumber!!.substring(0, lastNumber!!.length - 1)
            }

        }


        operationLine!!.text = valueOne


        if (lastOperation == "=") {
            resultLine!!.text = ""
        } else if (lastOperation != "=") {


            try {
                performCalculation(valueOne!!)
            } catch (ex: Exception) {
                operationLine!!.text = ""
                resultLine!!.text = ""
            }

        } else {


            resultLine!!.text = ""

        }


    }


    fun onOperationClick(view: View) {

        val button = view as Button
        lastOperation = button.text.toString()
        lastNumber = ""




        if (lastOperation == "=" && valueOne == "") {

            resultLine!!.text = operationLine!!.text
            operationLine!!.text = ""


        } else if (lastOperation != "=" && valueOne == "") {

            operationLine!!.text = ""
        } else if (lastOperation != "=") {


            if (valueOne!![valueOne!!.length - 1] == '×' || valueOne!![valueOne!!.length - 1] == '/' || valueOne!![valueOne!!.length - 1] == '+'
                || valueOne!![valueOne!!.length - 1] == '-' || valueOne!![valueOne!!.length - 1] == '%'
            ) {
                valueOne = valueOne!!.substring(0, valueOne!!.length - 1)
            }
            valueOne = valueOne!! + lastOperation!!
            operationLine!!.text = valueOne

        } else if (lastOperation != "=" && operationLine.toString() == "") {


            operationLine!!.text = valueOne!! + lastOperation!!

            resultLine!!.text = ""

        } else if (lastOperation == "=") {


            valueOne = resultLine!!.text.toString()
            operationLine!!.text = ""


        }


    }


    fun backspaceClick(view: View) {

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

    fun resetClick(view: View) {

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
