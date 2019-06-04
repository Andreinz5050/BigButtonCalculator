package com.example.android.bigbuttoncalculator


import android.content.res.Configuration
import android.graphics.drawable.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var valueOne: String = ""
    private var lastNumber: String = ""


    private var lastOperation: String? = "="
    private var resultLineSave = ""
    internal var operationLineSave = ""


    private var menuAdapter: ArrayAdapter<String>? = null
    private var drawerToggle: ActionBarDrawerToggle? = null


    private lateinit var menuAnimHamToCross: AnimatedVectorDrawable
    private lateinit var menuAnimCrossToHam: AnimatedVectorDrawable
    private lateinit  var startBtnMenu: VectorDrawable


    private lateinit var finishBtnMenu: VectorDrawable


    private var mMenuFlag = true

    //private var database = FirebaseDatabase.getInstance()
    //private val myRef = database.getReference("message")
    //val TAG = "MyMessage"





    override fun onCreate(savedInstanceState: Bundle?) {

        // Read from the database
        /*myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })*/


        setTheme(R.style.FeedActivityThemeLight)
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

    }

    /*private fun initFirebase()
    {
        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance()
    }
*/

    private fun addDrawerItems() {
        val osArray = arrayOf("Light", "Dark")
        menuAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, osArray)
        drawerList.adapter = menuAdapter

        drawerList.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                when (position) {
                    0 -> {
                        setTheme(R.style.FeedActivityThemeLight)
                        supportActionBar?.setHomeAsUpIndicator(menuAnimCrossToHam)

                        menuAnimCrossToHam.start()
                        drawerLayout.closeDrawer(Gravity.LEFT, false)
                        operationLine.text = operationLineSave
                    }

                    1 -> {
                        setTheme(R.style.FeedActivityThemeDark)
                        supportActionBar?.setHomeAsUpIndicator(menuAnimCrossToHam)
                        menuAnimCrossToHam.start()
                        drawerLayout.closeDrawer(Gravity.LEFT, false)
                        operationLine.text = operationLineSave
                    }
                }
            }
        }
    }

    private fun setupDrawer() {
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
        lastNumber += s

        var valueOneWithoutLast = ""


        if (lastNumber.length > 1) {
            valueOneWithoutLast = lastNumber.substring(0, lastNumber.length - 1)
        }
        var z = lastNumber.length
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
