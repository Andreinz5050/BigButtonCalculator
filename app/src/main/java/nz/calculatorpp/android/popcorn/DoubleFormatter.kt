package nz.calculatorpp.android.popcorn

import com.google.common.base.Preconditions
import com.google.common.base.Strings
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.pow


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

    private var ExpUp // always = 10^maxInteger
            = 0.0
    private var maxInteger = 0
    private var maxFraction = 0
    private var nfBelow: NumberFormat? = null
    private var nfNormal: NumberFormat? = null
    private var nfAbove: NumberFormat? = null

    private enum class NumberFormatKind {
        Below, Normal, Above
    }

    private fun setPrecision(maxInteger: Int, maxFraction: Int) {
        Preconditions.checkArgument(maxFraction >= 0)
        Preconditions.checkArgument(maxInteger in 1..16)
        if (maxFraction == this.maxFraction && this.maxInteger == maxInteger) {
            return
        }
        this.maxFraction = maxFraction
        this.maxInteger = maxInteger
        ExpUp = 10.0.pow(maxInteger.toDouble())
        nfBelow = createNumberFormat(NumberFormatKind.Below)
        nfNormal = createNumberFormat(NumberFormatKind.Normal)
        nfAbove = createNumberFormat(NumberFormatKind.Above)
    }

    private fun createNumberFormat(kind: NumberFormatKind): NumberFormat {

        // If you do not use the Guava library, replace it with createSharp(precision);
        val sharpByPrecision: String = Strings.repeat("#", maxFraction)
        val f = NumberFormat.getInstance(Locale.US)

        // Apply bankers' rounding:  this is the rounding mode that
        // statistically minimizes cumulative error when applied
        // repeatedly over a sequence of calculations
        f.roundingMode = RoundingMode.HALF_EVEN
        if (f is DecimalFormat) {
            val dfs = f.decimalFormatSymbols

            // Set group separator to space instead of comma

            //dfs.setGroupingSeparator(' ');

            // Set Exponent symbol to minus 'e' instead of 'E'
            if (kind == NumberFormatKind.Above) {
                dfs.exponentSeparator =
                    "e+" //force to display the positive sign in the exponent part
            } else {
                dfs.exponentSeparator = "e"
            }
            f.decimalFormatSymbols = dfs

            // Use exponent format if v is outside of [EXP_DOWN,EXP_UP]
            if (kind == NumberFormatKind.Normal) {
                if (maxFraction == 0) {
                    f.applyPattern("#,##0")
                } else {
                    f.applyPattern("#,##0.$sharpByPrecision")
                }
            } else {
                if (maxFraction == 0) {
                    f.applyPattern("0E0")
                } else {
                    f.applyPattern("0." + sharpByPrecision + "E0")
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
        val absv = abs(v)
        if (absv < EXP_DOWN) {
            return nfBelow!!.format(v)
        }
        return if (absv > ExpUp) {
            nfAbove!!.format(v)
        } else nfNormal!!.format(v)
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
        val sharpByPrecision: String = Strings.repeat("#", maxFraction)
        val absv = Math.abs(v)
        val f = NumberFormat.getInstance(Locale.US)

        // Apply bankers' rounding:  this is the rounding mode that
        // statistically minimizes cumulative error when applied
        // repeatedly over a sequence of calculations
        f.roundingMode = RoundingMode.HALF_EVEN
        if (f is DecimalFormat) {
            val dfs = f.decimalFormatSymbols

            // Set group separator to space instead of comma
            dfs.groupingSeparator = ' '

            // Set Exponent symbol to minus 'e' instead of 'E'
            if (absv > ExpUp) {
                dfs.exponentSeparator =
                    "e+" //force to display the positive sign in the exponent part
            } else {
                dfs.exponentSeparator = "e"
            }
            f.decimalFormatSymbols = dfs

            //use exponent format if v is out side of [EXP_DOWN,EXP_UP]
            if (absv < EXP_DOWN || absv > ExpUp) {
                f.applyPattern("0." + sharpByPrecision + "E0")
            } else {
                f.applyPattern("#,##0.$sharpByPrecision")
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