package com.example.android.bigbuttoncalculator


import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import javax.inject.Inject


abstract class BaseThemeFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var valueOne: String
    private lateinit var lastNumber: String


    private lateinit var lastOperation: String
    private lateinit var operationLineSave: String

    private lateinit var btnZeroAnim: AnimatedVectorDrawable
    private lateinit var btnOneAnim: AnimatedVectorDrawable
    private lateinit var btnTwoAnim: AnimatedVectorDrawable
    private lateinit var btnThreeAnim: AnimatedVectorDrawable
    private lateinit var btnFourAnim: AnimatedVectorDrawable
    private lateinit var btnFiveAnim: AnimatedVectorDrawable
    private lateinit var btnSixAnim: AnimatedVectorDrawable
    private lateinit var btnSevenAnim: AnimatedVectorDrawable
    private lateinit var btnEightAnim: AnimatedVectorDrawable
    private lateinit var btnNineAnim: AnimatedVectorDrawable
    private lateinit var btnDotAnim: AnimatedVectorDrawable

    private lateinit var btnEqualAnim: AnimatedVectorDrawable
    private lateinit var btnPluslAnim: AnimatedVectorDrawable
    private lateinit var btnMinuslAnim: AnimatedVectorDrawable
    private lateinit var btnMultiplyAnim: AnimatedVectorDrawable
    private lateinit var btnDivisionlAnim: AnimatedVectorDrawable
    private lateinit var btnPercentlAnim: AnimatedVectorDrawable

    private lateinit var btnBackspaceAnim: AnimatedVectorDrawable
    private lateinit var btnResetAnim: AnimatedVectorDrawable


    private lateinit var operationLine: TextView
    private lateinit var resultLine: TextView


    private lateinit var menuButton: Button
    private lateinit var zeroButton: Button
    private lateinit var oneButton: Button
    private lateinit var twoButton: Button
    private lateinit var threeButton: Button
    private lateinit var fourButton: Button
    private lateinit var fiveButton: Button
    private lateinit var sixButton: Button
    private lateinit var sevenButton: Button
    private lateinit var eightButton: Button
    private lateinit var nineButton: Button
    private lateinit var dotButton: Button

    private lateinit var backSpButton: Button
    private lateinit var resetButton: Button

    private lateinit var percentageButton: Button
    private lateinit var divideButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var minusButton: Button
    private lateinit var plusButton: Button
    private lateinit var equalButton: Button


    private lateinit var viewModel: BaseThemeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.light_fragment_layout, container, false)
        initializeFields(view)
        setUp()


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BaseThemeViewModel::class.java)


    }

    fun upDateOperationAndResultLines(pair: Pair<String, String>) {
        operationLine.text = pair.first
        resultLine.text = pair.second
    }


    fun initializeFields(view: View) {
        valueOne = ""
        lastNumber = ""
        lastOperation = ""
        operationLineSave = ""

        resultLine = view.findViewById(R.id.resultLine)
        operationLine = view.findViewById(R.id.operationLine)

        menuButton = view.findViewById(R.id.menuButton)
        zeroButton = view.findViewById<Button>(R.id.btn_zero).apply {
            setBackgroundResource(R.drawable.anim_btn_zero)
            btnZeroAnim = background as AnimatedVectorDrawable
        }
        oneButton = view.findViewById<Button>(R.id.btn_one).apply {
            setBackgroundResource(R.drawable.anim_btn_one)
            btnOneAnim = background as AnimatedVectorDrawable
        }

        twoButton = view.findViewById<Button>(R.id.btn_two).apply {
            setBackgroundResource(R.drawable.anim_btn_two)
            btnTwoAnim = background as AnimatedVectorDrawable
        }
        threeButton = view.findViewById<Button>(R.id.btn_three).apply {
            setBackgroundResource(R.drawable.anim_btn_three)
            btnThreeAnim = background as AnimatedVectorDrawable
        }
        fourButton = view.findViewById<Button>(R.id.btn_four).apply {
            setBackgroundResource(R.drawable.anim_btn_four)
            btnFourAnim = background as AnimatedVectorDrawable
        }
        fiveButton = view.findViewById<Button>(R.id.btn_five).apply {
            setBackgroundResource(R.drawable.anim_btn_five)
            btnFiveAnim = background as AnimatedVectorDrawable
        }
        sixButton = view.findViewById<Button>(R.id.btn_six).apply {
            setBackgroundResource(R.drawable.anim_btn_six)
            btnSixAnim = background as AnimatedVectorDrawable
        }
        sevenButton = view.findViewById<Button>(R.id.btn_seven).apply {
            setBackgroundResource(R.drawable.anim_btn_seven)
            btnSevenAnim = background as AnimatedVectorDrawable
        }
        eightButton = view.findViewById<Button>(R.id.btn_eight).apply {
            setBackgroundResource(R.drawable.anim_btn_eight)
            btnEightAnim = background as AnimatedVectorDrawable
        }
        nineButton = view.findViewById<Button>(R.id.btn_nine).apply {
            setBackgroundResource(R.drawable.anim_btn_nine)
            btnNineAnim = background as AnimatedVectorDrawable
        }
        dotButton = view.findViewById<Button>(R.id.btn_dot).apply {
            setBackgroundResource(R.drawable.anim_btn_dot)
            btnDotAnim = background as AnimatedVectorDrawable
        }

        backSpButton = view.findViewById<Button>(R.id.btn_back).apply {
            setBackgroundResource(R.drawable.anim_btn_backspace)
            btnBackspaceAnim = background as AnimatedVectorDrawable
        }
        resetButton = view.findViewById<Button>(R.id.btn_reset).apply {
            setBackgroundResource(R.drawable.anim_btn_reset)
            btnResetAnim = background as AnimatedVectorDrawable
        }

        percentageButton = view.findViewById<Button>(R.id.btn_percentage).apply {
            setBackgroundResource(R.drawable.anim_btn_percentage)
            btnPercentlAnim = background as AnimatedVectorDrawable
        }
        divideButton = view.findViewById<Button>(R.id.btn_divide).apply {
            setBackgroundResource(R.drawable.anim_btn_division)
            btnDivisionlAnim = background as AnimatedVectorDrawable
        }
        multiplyButton = view.findViewById<Button>(R.id.btn_multiple).apply {
            setBackgroundResource(R.drawable.anim_btn_multiply)
            btnMultiplyAnim = background as AnimatedVectorDrawable
        }
        minusButton = view.findViewById<Button>(R.id.btn_minus).apply {
            setBackgroundResource(R.drawable.anim_btn_minus)
            btnMinuslAnim = background as AnimatedVectorDrawable
        }
        plusButton = view.findViewById<Button>(R.id.btn_plus).apply {
            setBackgroundResource(R.drawable.anim_btn_plus)
            btnPluslAnim = background as AnimatedVectorDrawable
        }
        equalButton = view.findViewById<Button>(R.id.btn_equal).apply {
            setBackgroundResource(R.drawable.anim_btn_equal)
            btnEqualAnim = background as AnimatedVectorDrawable
        }


    }


    fun setUp() {
        zeroButton.setOnClickListener(this)
        oneButton.setOnClickListener(this)
        twoButton.setOnClickListener(this)
        threeButton.setOnClickListener(this)
        fourButton.setOnClickListener(this)
        fiveButton.setOnClickListener(this)
        sixButton.setOnClickListener(this)
        sevenButton.setOnClickListener(this)
        eightButton.setOnClickListener(this)
        nineButton.setOnClickListener(this)
        dotButton.setOnClickListener(this)

        backSpButton.setOnClickListener(this)
        resetButton.setOnClickListener(this)

        menuButton.setOnClickListener { this.findNavController().navigate(R.id.menuFragment) }
        percentageButton.setOnClickListener(this)
        divideButton.setOnClickListener(this)
        multiplyButton.setOnClickListener(this)
        minusButton.setOnClickListener(this)
        plusButton.setOnClickListener(this)
        equalButton.setOnClickListener(this)


    }

    override fun onClick(view: View?) {

        when (view) {

            zeroButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("0"))
                zeroButton.setBackground(btnZeroAnim)
                btnZeroAnim.start()
            }
            oneButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("1"))
                oneButton.setBackground(btnOneAnim)
                btnOneAnim.start()
            }
            twoButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("2"))
                twoButton.setBackground(btnTwoAnim)
                btnTwoAnim.start()
            }
            threeButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("3"))
                threeButton.setBackground(btnThreeAnim)
                btnThreeAnim.start()
            }
            fourButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("4"))
                fourButton.setBackground(btnFourAnim)
                btnFourAnim.start()
            }
            fiveButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("5"))
                fiveButton.setBackground(btnFiveAnim)
                btnFiveAnim.start()
            }
            sixButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("6"))
                sixButton.setBackground(btnSixAnim)
                btnSixAnim.start()
            }
            sevenButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("7"))
                sevenButton.setBackground(btnSevenAnim)
                btnSevenAnim.start()
            }
            eightButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("8"))
                eightButton.setBackground(btnEightAnim)
                btnEightAnim.start()
            }
            nineButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("9"))
                nineButton.setBackground(btnNineAnim)
                btnNineAnim.start()
            }
            dotButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("."))
                dotButton.setBackground(btnDotAnim)
                btnDotAnim.start()
            }

            backSpButton -> {
                upDateOperationAndResultLines(viewModel.backspaceClick())
                backSpButton.setBackground(btnBackspaceAnim)
                btnBackspaceAnim.start()
            }
            resetButton -> {
                upDateOperationAndResultLines(viewModel.resetClick())
                resetButton.setBackground(btnResetAnim)
                btnResetAnim.start()
            }

            percentageButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("%"))
                percentageButton.setBackground(btnPercentlAnim)
                btnPercentlAnim.start()
            }
            divideButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("/"))
                divideButton.setBackground(btnDivisionlAnim)
                btnDivisionlAnim.start()
            }
            multiplyButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("×"))
                multiplyButton.setBackground(btnMultiplyAnim)
                btnMultiplyAnim.start()
            }
            minusButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("-"))
                minusButton.setBackground(btnMinuslAnim)
                btnMinuslAnim.start()
            }
            plusButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("+"))
                plusButton.setBackground(btnPluslAnim)
                btnPluslAnim.start()
            }
            equalButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("="))
                equalButton.setBackground(btnEqualAnim)
                btnEqualAnim.start()
            }


        }
    }


}