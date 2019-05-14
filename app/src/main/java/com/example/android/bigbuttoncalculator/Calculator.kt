package com.example.android.bigbuttoncalculator

object Calculator {

    fun calculate(expression: String?): Double? {
        return if (expression == null || expression.length == 0) {
            null
        } else calc(expression.replace(" ", ""))
    }

    fun calc(expression: String): Double? {
        var expression = expression

        if (expression.startsWith("(") && expression.endsWith(")")) {
            return calc(expression.substring(1, expression.length - 1))
        }
        val containerArr = arrayOf(expression)
        var leftVal = getNextOperand(containerArr)
        expression = containerArr[0]
        if (expression.length == 0) {
            return leftVal
        }
        var operator = expression[0]
        expression = expression.substring(1)

        while (operator == '×' || operator == '/' || operator == '%') {
            containerArr[0] = expression
            val rightVal = getNextOperand(containerArr)
            expression = containerArr[0]
            if (operator == '×') {
                leftVal = leftVal * rightVal
            } else if (operator == '/') {
                leftVal = leftVal / rightVal
            } else if (operator == '%') {
                leftVal = leftVal / 100 * rightVal
            }
            if (expression.length > 0) {
                operator = expression[0]
                expression = expression.substring(1)
            } else {
                return leftVal
            }
        }
        return if (operator == '+') {
            leftVal + calc(expression)!!
        } else {
            leftVal - calc(expression)!!
        }


    }

    private fun getNextOperand(exp: Array<String>): Double {
        val res: Double
        if (exp[0].startsWith("(")) {
            var open = 1
            var i = 1
            while (open != 0) {
                if (exp[0][i] == '(') {
                    open++
                } else if (exp[0][i] == ')') {
                    open--
                }
                i++
            }
            res = calc(exp[0].substring(1, i - 1))!!
            exp[0] = exp[0].substring(i)
        } else {
            var i = 1
            if (exp[0][0] == '-') {
                i++
            }
            while (exp[0].length > i && isNumber(exp[0][i].toInt())) {
                i++
            }
            res = java.lang.Double.parseDouble(exp[0].substring(0, i))
            exp[0] = exp[0].substring(i)
        }
        return res
    }


    private fun isNumber(c: Int): Boolean {
        val zero = '0'.toInt()
        val nine = '9'.toInt()
        return c >= zero && c <= nine || c == '.'.toInt()
    }


}