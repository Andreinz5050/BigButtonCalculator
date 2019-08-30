package com.example.android.bigbuttoncalculator


import android.graphics.drawable.AnimatedVectorDrawable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController


abstract class BaseThemeFragment : Fragment(), View.OnClickListener {


    private lateinit var valueOne: String
    private lateinit var lastNumber: String


    private lateinit var lastOperation: String
    private lateinit var operationLineSave: String

    private lateinit var operationLine: TextView
    private lateinit var resultLine: TextView


    lateinit var menuButton: Button
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

        }
        oneButton = view.findViewById<Button>(R.id.btn_one).apply {
            setBackgroundResource(R.drawable.anim_btn_one)

        }

        twoButton = view.findViewById<Button>(R.id.btn_two).apply {
            setBackgroundResource(R.drawable.anim_btn_two)

        }
        threeButton = view.findViewById<Button>(R.id.btn_three).apply {
            setBackgroundResource(R.drawable.anim_btn_three)

        }
        fourButton = view.findViewById<Button>(R.id.btn_four).apply {
            setBackgroundResource(R.drawable.anim_btn_four)

        }
        fiveButton = view.findViewById<Button>(R.id.btn_five).apply {
            setBackgroundResource(R.drawable.anim_btn_five)

        }
        sixButton = view.findViewById<Button>(R.id.btn_six).apply {
            setBackgroundResource(R.drawable.anim_btn_six)

        }
        sevenButton = view.findViewById<Button>(R.id.btn_seven).apply {
            setBackgroundResource(R.drawable.anim_btn_seven)

        }
        eightButton = view.findViewById<Button>(R.id.btn_eight).apply {
            setBackgroundResource(R.drawable.anim_btn_eight)

        }
        nineButton = view.findViewById<Button>(R.id.btn_nine).apply {
            setBackgroundResource(R.drawable.anim_btn_nine)

        }
        dotButton = view.findViewById<Button>(R.id.btn_dot).apply {
            setBackgroundResource(R.drawable.anim_btn_dot)

        }

        backSpButton = view.findViewById<Button>(R.id.btn_back).apply {
            setBackgroundResource(R.drawable.anim_btn_backspace)

        }
        resetButton = view.findViewById<Button>(R.id.btn_reset).apply {
            setBackgroundResource(R.drawable.anim_btn_reset)

        }

        percentageButton = view.findViewById<Button>(R.id.btn_percentage).apply {
            setBackgroundResource(R.drawable.anim_btn_percentage)

        }
        divideButton = view.findViewById<Button>(R.id.btn_divide).apply {
            setBackgroundResource(R.drawable.anim_btn_division)

        }
        multiplyButton = view.findViewById<Button>(R.id.btn_multiple).apply {
            setBackgroundResource(R.drawable.anim_btn_multiply)

        }
        minusButton = view.findViewById<Button>(R.id.btn_minus).apply {
            setBackgroundResource(R.drawable.anim_btn_minus)

        }
        plusButton = view.findViewById<Button>(R.id.btn_plus).apply {
            setBackgroundResource(R.drawable.anim_btn_plus)

        }
        equalButton = view.findViewById<Button>(R.id.btn_equal).apply {
            setBackgroundResource(R.drawable.anim_btn_equal)

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

        menuButton.setOnClickListener { this.findNavController().navigate(R.id.action_lightFragment_to_menuFragment) }
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
                (zeroButton.background as AnimatedVectorDrawable).start()
            }
            oneButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("1"))

                (oneButton.background as AnimatedVectorDrawable).start()
            }
            twoButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("2"))
                (twoButton.background as AnimatedVectorDrawable).start()

            }
            threeButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("3"))
                (threeButton.background as AnimatedVectorDrawable).start()

            }
            fourButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("4"))
                (fourButton.background as AnimatedVectorDrawable).start()

            }
            fiveButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("5"))
                (fiveButton.background as AnimatedVectorDrawable).start()

            }
            sixButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("6"))
                (sixButton.background as AnimatedVectorDrawable).start()

            }
            sevenButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("7"))
                (sevenButton.background as AnimatedVectorDrawable).start()

            }
            eightButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("8"))
                (eightButton.background as AnimatedVectorDrawable).start()

            }
            nineButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("9"))
                (nineButton.background as AnimatedVectorDrawable).start()

            }
            dotButton -> {
                upDateOperationAndResultLines(viewModel.onNumberClick("."))
                (dotButton.background as AnimatedVectorDrawable).start()

            }

            backSpButton -> {
                upDateOperationAndResultLines(viewModel.backspaceClick())
                (backSpButton.background as AnimatedVectorDrawable).start()

            }
            resetButton -> {
                upDateOperationAndResultLines(viewModel.resetClick())
                (resetButton.background as AnimatedVectorDrawable).start()

            }

            percentageButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("%"))
                (percentageButton.background as AnimatedVectorDrawable).start()

            }
            divideButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("/"))
                (divideButton.background as AnimatedVectorDrawable).start()

            }
            multiplyButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("×"))
                (multiplyButton.background as AnimatedVectorDrawable).start()

            }
            minusButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("-"))
                (minusButton.background as AnimatedVectorDrawable).start()

            }
            plusButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("+"))
                (plusButton.background as AnimatedVectorDrawable).start()

            }
            equalButton -> {
                upDateOperationAndResultLines(viewModel.onOperationClick("="))
                (equalButton.background as AnimatedVectorDrawable).start()

            }


        }
    }


}