package nz.calculatorpp.android.popcorn

import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.timerTask


class BaseThemeViewModel : ViewModel() {


    private var valueOne = ""
    private var lastNumber = ""


    private var lastOperation = "="

    private var result = ""
    private var toEvaluate = ""

    var strings = arrayOf(valueOne, lastNumber, lastOperation, result, toEvaluate)


    fun onNumberClick(string: String): Pair<String, String> {


        valueOne += string
        lastNumber += string

        var valueOneWithoutLast = ""


        if (lastNumber.length > 1) {
            valueOneWithoutLast = lastNumber.substring(0, lastNumber.length - 1)
        }

        if (lastNumber.length >= 2) {
            if (lastNumber[lastNumber.length - 1] == '.' && lastNumber[lastNumber.length - 2] == '.') {
                valueOne = valueOne.substring(0, valueOne.length - 1)
                lastNumber = lastNumber.substring(0, lastNumber.length - 1)
            } else if (valueOneWithoutLast.contains(".") && string == ".") {
                valueOne = valueOne.substring(0, valueOne.length - 1)
                lastNumber = lastNumber.substring(0, lastNumber.length - 1)
            }

        }


        toEvaluate = valueOne


        when {
            lastOperation == "=" -> {
                result = ""
                saveStrings()
                return Pair(toEvaluate, result)
            }
            lastOperation != "=" -> {


                return try {
                    result = performCalculation(toEvaluate)
                    saveStrings()
                    Pair(toEvaluate, result)
                } catch (ex: Exception) {

                    result = ""
                    toEvaluate = ""
                    saveStrings()
                    Pair(toEvaluate, result)
                }


            }
            else -> {


                result = ""
                saveStrings()
                return Pair(toEvaluate, result)

            }
        }


    }

    fun onOperationClick(string: String): Pair<String, String> {

        lastOperation = string
        lastNumber = ""

        if(toEvaluate =="22.01×1988" && result =="43,755.88")
        {
            result = "Оля!"
            toEvaluate = "С Днем Рождения, "
            val timer = Timer()
            timer.schedule(timerTask
            {
                toEvaluate =" 22.01×1988"
                result = "43,755.88"
                saveStrings()
                onOperationClick("=")
            }, 100)
            saveStrings()
            return Pair(toEvaluate, result)
        }
        else
        {

        if (lastOperation == "=" && valueOne == "") {

            result = toEvaluate
            toEvaluate = ""
            saveStrings()
            return Pair(toEvaluate, result)


        } else if (lastOperation != "=" && valueOne == "") {

            toEvaluate = ""
            saveStrings()
            return Pair(toEvaluate, result)
        } else if (lastOperation != "=") {


            if (valueOne[valueOne.length - 1] == '×' || valueOne[valueOne.length - 1] == '/' || valueOne[valueOne.length - 1] == '+'
                || valueOne[valueOne.length - 1] == '-' || valueOne[valueOne.length - 1] == '%'
            ) {
                valueOne = valueOne.substring(0, valueOne.length - 1)
            }
            valueOne += lastOperation
            toEvaluate = valueOne
            saveStrings()
            return Pair(toEvaluate, result)

        } else if (lastOperation != "=" && toEvaluate == "") {


            toEvaluate = valueOne + lastOperation
            result = ""
            saveStrings()
            return Pair(toEvaluate, result)

        } else if (lastOperation == "=" && result == "") {


            result = toEvaluate
            valueOne = toEvaluate
            toEvaluate = ""
            saveStrings()
            return Pair(toEvaluate, result)


        }
        else if (lastOperation == "=") {


            valueOne = result
            toEvaluate = ""
            saveStrings()
            return Pair(toEvaluate, result)


        }
        saveStrings()
        return Pair(toEvaluate, result)
        }
    }

    fun backspaceClick(): Pair<String, String> {

        var str = toEvaluate
        if (str != "") {
            str = str.substring(0, str.length - 1)
            toEvaluate = str
            valueOne = str

            return try {

                result = performCalculation(str)
                Pair(toEvaluate, result)
            } catch (ex: Exception) {
                result = ""
                toEvaluate = ""
                saveStrings()
                Pair(toEvaluate, result)
            }

        } else {
            lastOperation = "="
            result = ""
            toEvaluate = ""
            saveStrings()
            return Pair(toEvaluate, result)
        }


    }

    fun resetClick(): Pair<String, String> {
        saveStrings()
        if(toEvaluate =="22.01×1988")
        {
            valueOne = ""
            lastOperation = "="
            result = "Оля!"
            toEvaluate = "С Днем Рождения, "

            val timer = Timer()
            timer.schedule(timerTask
            {
                toEvaluate = ""
                resetClick()
            }, 100)
        }
        else
        {
            valueOne = ""
            lastOperation = "="
            result = ""
            toEvaluate = ""
        }
        saveStrings()
        return Pair(toEvaluate, result)
    }

    private fun performCalculation(st: String): String {
        var tempString = st
        var ifNoZero: String
        val result: String
        val beautyNumber: String




        if (st[st.length - 1] == '×' || st[st.length - 1] == '/' || st[st.length - 1] == '+' || st[st.length - 1] == '-' || st[st.length - 1] == '%') {
            tempString = tempString.substring(0, tempString.length - 1)


            ifNoZero = Calculator.calculate(
                tempString
            )!!.toString()
            if (ifNoZero[ifNoZero.length - 1] == '0' && ifNoZero[ifNoZero.length - 2] == '.') {


                ifNoZero = ifNoZero.substring(0, ifNoZero.length - 1)
                result = ifNoZero.substring(0, ifNoZero.length - 1)
            } else {
                result = Calculator.calculate(
                    tempString
                )!!.toString()
            }


        } else {


            ifNoZero = Calculator.calculate(
                st
            )!!.toString()
            if (ifNoZero[ifNoZero.length - 1] == '0' && ifNoZero[ifNoZero.length - 2] == '.') {
                ifNoZero = ifNoZero.substring(0, ifNoZero.length - 1)
                result = ifNoZero.substring(0, ifNoZero.length - 1)
            } else {
                result = ifNoZero
            }


        }

        val horribleNumber: Double
        horribleNumber = result.toDouble()
        val df = DoubleFormatter(
            7,
            8
        ) // 7 = MaxInteger, 8 = MaxDecimal
        beautyNumber = df.format(horribleNumber)
        beautyNumber.replace(",", "")
        saveStrings()
        return beautyNumber

    }
    private fun saveStrings() {
        strings = arrayOf(valueOne, lastNumber, lastOperation, result, toEvaluate)
    }

}