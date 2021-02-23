package com.example.android.bigbuttoncalculator

import com.google.common.base.Preconditions
import com.google.common.base.Strings
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


/**
 * Convert a double to a beautiful String (US-local):
 *
 * double horribleNumber = 3598945.141658554548844;
 * DoubleFormatter df = new DoubleFormatter(4,6);
 * String beautyDisplay = df.format(horribleNumber);
 * String beautyLabel = df.formatHtml(horribleNumber);
 *
 * Manipulate 3 instances of NumberFormat to efficiently format a great number of double values.
 * (avoid to create an object NumberFormat each call of format()).
 *
 * 3 instances of NumberFormat will be reused to format a value v:
 *
 * if v < EXP_DOWN, uses nfBelow
 * if EXP_DOWN <= v <= EXP_UP, uses nfNormal
 * if EXP_UP < v, uses nfAbove
 *
 * nfBelow, nfNormal and nfAbove will be generated base on the precision_ parameter.
 *
 *
 */
class DoubleFormatter(maxInteger: Int, maxFraction: Int) {
    private var EXP_UP // always = 10^maxInteger
            = 0.0
    private var maxInteger_ = 0
    private var maxFraction_ = 0
    private var nfBelow_: NumberFormat? = null
    private var nfNormal_: NumberFormat? = null
    private var nfAbove_: NumberFormat? = null

    private enum class NumberFormatKind {
        Below, Normal, Above
    }

    fun setPrecision(maxInteger: Int, maxFraction: Int) {
        Preconditions.checkArgument(maxFraction >= 0)
        Preconditions.checkArgument(maxInteger > 0 && maxInteger < 17)
        if (maxFraction == maxFraction_ && maxInteger_ == maxInteger) {
            return
        }
        maxFraction_ = maxFraction
        maxInteger_ = maxInteger
        EXP_UP = Math.pow(10.0, maxInteger.toDouble())
        nfBelow_ = createNumberFormat(NumberFormatKind.Below)
        nfNormal_ = createNumberFormat(NumberFormatKind.Normal)
        nfAbove_ = createNumberFormat(NumberFormatKind.Above)
    }

    private fun createNumberFormat(kind: NumberFormatKind): NumberFormat {

        // If you do not use the Guava library, replace it with createSharp(precision);
        val sharpByPrecision: String = Strings.repeat("#", maxFraction_)
        val f = NumberFormat.getInstance(Locale.US)

        // Apply bankers' rounding:  this is the rounding mode that
        // statistically minimizes cumulative error when applied
        // repeatedly over a sequence of calculations
        f.roundingMode = RoundingMode.HALF_EVEN
        if (f is DecimalFormat) {
            val df = f
            val dfs = df.decimalFormatSymbols

            // Set group separator to space instead of comma

            //dfs.setGroupingSeparator(' ');

            // Set Exponent symbol to minus 'e' instead of 'E'
            if (kind == NumberFormatKind.Above) {
                dfs.exponentSeparator =
                    "e+" //force to display the positive sign in the exponent part
            } else {
                dfs.exponentSeparator = "e"
            }
            df.decimalFormatSymbols = dfs

            // Use exponent format if v is outside of [EXP_DOWN,EXP_UP]
            if (kind == NumberFormatKind.Normal) {
                if (maxFraction_ == 0) {
                    df.applyPattern("#,##0")
                } else {
                    df.applyPattern("#,##0.$sharpByPrecision")
                }
            } else {
                if (maxFraction_ == 0) {
                    df.applyPattern("0E0")
                } else {
                    df.applyPattern("0." + sharpByPrecision + "E0")
                }
            }
        }
        return f
    }

    fun format(v: Double): String {
        if (java.lang.Double.isNaN(v)) {
            return "-"
        }
        if (v == 0.0) {
            return "0"
        }
        val absv = Math.abs(v)
        if (absv < EXP_DOWN) {
            return nfBelow_!!.format(v)
        }
        return if (absv > EXP_UP) {
            nfAbove_!!.format(v)
        } else nfNormal_!!.format(v)
    }

    /**
     * Format and higlight the important part (integer part & exponent part)
     */
    fun formatHtml(v: Double): String {
        return if (java.lang.Double.isNaN(v)) {
            "-"
        } else htmlize(format(v))
    }

    /**
     * This is the base alogrithm: create a instance of NumberFormat for the value, then format it. It should
     * not be used to format a great numbers of value
     *
     * We will never use this methode, it is here only to understanding the Algo principal:
     *
     * format v to string. precision_ is numbers of digits after decimal.
     * if EXP_DOWN <= abs(v) <= EXP_UP, display the normal format: 124.45678
     * otherwise display scientist format with: 1.2345e+30
     *
     * pre-condition: precision >= 1
     */
    @Deprecated("")
    fun formatInefficient(v: Double): String {

        // If you do not use Guava library, replace with createSharp(precision);
        val sharpByPrecision: String = Strings.repeat("#", maxFraction_)
        val absv = Math.abs(v)
        val f = NumberFormat.getInstance(Locale.US)

        // Apply bankers' rounding:  this is the rounding mode that
        // statistically minimizes cumulative error when applied
        // repeatedly over a sequence of calculations
        f.roundingMode = RoundingMode.HALF_EVEN
        if (f is DecimalFormat) {
            val df = f
            val dfs = df.decimalFormatSymbols

            // Set group separator to space instead of comma
            dfs.groupingSeparator = ' '

            // Set Exponent symbol to minus 'e' instead of 'E'
            if (absv > EXP_UP) {
                dfs.exponentSeparator =
                    "e+" //force to display the positive sign in the exponent part
            } else {
                dfs.exponentSeparator = "e"
            }
            df.decimalFormatSymbols = dfs

            //use exponent format if v is out side of [EXP_DOWN,EXP_UP]
            if (absv < EXP_DOWN || absv > EXP_UP) {
                df.applyPattern("0." + sharpByPrecision + "E0")
            } else {
                df.applyPattern("#,##0.$sharpByPrecision")
            }
        }
        return f.format(v)
    }

    companion object {
        private const val EXP_DOWN = 1e-3

        /**
         * Convert "3.1416e+12" to "**3**.1416e**+12**"
         * It is a html format of a number which highlight the integer and exponent part
         */
        private fun htmlize(s: String): String {
            val resu = StringBuilder("<b>")
            var p1 = s.indexOf('.')
            if (p1 > 0) {
                resu.append(s.substring(0, p1))
                resu.append("</b>")
            } else {
                p1 = 0
            }
            val p2 = s.lastIndexOf('e')
            if (p2 > 0) {
                resu.append(s.substring(p1, p2))
                resu.append("<b>")
                resu.append(s.substring(p2, s.length))
                resu.append("</b>")
            } else {
                resu.append(s.substring(p1, s.length))
                if (p1 == 0) {
                    resu.append("</b>")
                }
            }
            return resu.toString()
        }
    }

    init {
        setPrecision(maxInteger, maxFraction)
    }
}