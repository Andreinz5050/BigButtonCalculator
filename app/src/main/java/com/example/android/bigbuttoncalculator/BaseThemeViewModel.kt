package com.example.android.bigbuttoncalculator

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class BaseThemeViewModel : ViewModel() {


    private var valueOne = ""
    private var lastNumber = ""


    private var lastOperation = "="

    private var result = ""
    private var toEvaluate = ""


    fun onNumberClick(string: String): Pair<String, String> {


        val s = string
        valueOne += s
        lastNumber += s

        var valueOneWithoutLast = ""


        if (lastNumber.length > 1) {
            valueOneWithoutLast = lastNumber.substring(0, lastNumber.length - 1)
        }

        if (lastNumber.length >= 2) {
            if (lastNumber[lastNumber.length - 1] == '.' && lastNumber[lastNumber.length - 2] == '.') {
                valueOne = valueOne.substring(0, valueOne.length - 1)
                lastNumber = lastNumber.substring(0, lastNumber.length - 1)
            } else if (valueOneWithoutLast.contains(".") && s == ".") {
                valueOne = valueOne.substring(0, valueOne.length - 1)
                lastNumber = lastNumber.substring(0, lastNumber.length - 1)
            }

        }


        toEvaluate = valueOne


        if (lastOperation == "=") {
            result = ""
            return Pair(toEvaluate, result)
        } else if (lastOperation != "=") {


            try {
                result = performCalculation(toEvaluate)
                return Pair(toEvaluate, result)
            } catch (ex: Exception) {

                result = ""
                toEvaluate = ""
                return Pair(toEvaluate, result)
            }


        } else {


            result = ""
            return Pair(toEvaluate, result)

        }


    }

    fun onOperationClick(string: String): Pair<String, String> {

        lastOperation = string
        lastNumber = ""




        if (lastOperation == "=" && valueOne == "") {

            result = toEvaluate
            toEvaluate = ""
            return Pair(toEvaluate, result)


        } else if (lastOperation != "=" && valueOne == "") {

            toEvaluate = ""
            return Pair(toEvaluate, result)
        } else if (lastOperation != "=") {


            if (valueOne[valueOne.length - 1] == '×' || valueOne[valueOne.length - 1] == '/' || valueOne[valueOne.length - 1] == '+'
                || valueOne[valueOne.length - 1] == '-' || valueOne[valueOne.length - 1] == '%'
            ) {
                valueOne = valueOne.substring(0, valueOne.length - 1)
            }
            valueOne += lastOperation
            toEvaluate = valueOne
            return Pair(toEvaluate, result)

        } else if (lastOperation != "=" && toEvaluate == "") {


            toEvaluate = valueOne + lastOperation
            result = ""
            return Pair(toEvaluate, result)

        } else if (lastOperation == "=") {


            valueOne = result
            toEvaluate = ""
            return Pair(toEvaluate, result)


        }
        return Pair(toEvaluate, result)

    }

    fun backspaceClick(): Pair<String, String> {

        var str = toEvaluate
        if (str != "") {
            str = str.substring(0, str.length - 1)
            toEvaluate = str
            valueOne = str

            try {

                result = performCalculation(str)
                return Pair(toEvaluate, result)
            } catch (ex: Exception) {
                result = ""
                toEvaluate = ""
                return Pair(toEvaluate, result)
            }

        } else {
            lastOperation = "="
            result = ""
            toEvaluate = ""
            return Pair(toEvaluate, result)
        }


    }

    fun resetClick(): Pair<String, String> {

        valueOne = ""
        lastOperation = "="
        result = ""
        toEvaluate = ""
        return Pair(toEvaluate, result)
    }

    private fun performCalculation(st: String): String {
        var tempString = st
        var ifNoZero: String






        if (st[st.length - 1] == '×' || st[st.length - 1] == '/' || st[st.length - 1] == '+' || st[st.length - 1] == '-' || st[st.length - 1] == '%') {
            tempString = tempString.substring(0, tempString.length - 1)


            ifNoZero = Calculator.calculate(tempString)!!.toString()
            if (ifNoZero[ifNoZero.length - 1] == '0' && ifNoZero[ifNoZero.length - 2] == '.') {


                ifNoZero = ifNoZero.substring(0, ifNoZero.length - 1)
                return (ifNoZero.substring(0, ifNoZero.length - 1))
            } else {
                return (Calculator.calculate(tempString)!!.toString())
            }


        } else {


            ifNoZero = Calculator.calculate(st)!!.toString()
            if (ifNoZero[ifNoZero.length - 1] == '0' && ifNoZero[ifNoZero.length - 2] == '.') {
                ifNoZero = ifNoZero.substring(0, ifNoZero.length - 1)
                return (ifNoZero.substring(0, ifNoZero.length - 1))
            } else {
                return (Calculator.calculate(st)!!.toString())
            }


        }

    }
}