package com.example.android.bigbuttoncalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject

class BaseThemeModel @Inject constructor() : ViewModel() {

    private lateinit var view: BaseThemeView
    //private lateinit var valueOne: String
    //private lateinit var lastNumber: String



    fun onNumberClick(view: View) {


        val button = view as Button
        val s = button.text.toString()
        view.upDateOperationLine()
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


        operationLine.text = valueOne


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


            if (valueOne[valueOne.length - 1] == '×' || valueOne[valueOne.length - 1] == '/' || valueOne[valueOne.length - 1] == '+'
                || valueOne[valueOne.length - 1] == '-' || valueOne[valueOne.length - 1] == '%'
            ) {
                valueOne = valueOne.substring(0, valueOne.length - 1)
            }
            valueOne += lastOperation
            operationLine!!.text = valueOne

        } else if (lastOperation != "=" && operationLine.toString() == "") {


            operationLine!!.text = valueOne + lastOperation

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
}