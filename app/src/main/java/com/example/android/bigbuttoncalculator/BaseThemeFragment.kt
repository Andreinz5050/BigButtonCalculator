package com.example.android.bigbuttoncalculator


import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button

import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment

import javax.inject.Inject

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

 abstract class BaseThemeFragment : Fragment(), View.OnClickListener {
     @Inject
     lateinit var viewModelFactory: ViewModelProvider.Factory

     private lateinit var valueOne: String
    private lateinit var lastNumber: String


    private lateinit var lastOperation: String
    private lateinit var operationLineSave: String


    private lateinit var menuAdapter: ArrayAdapter<String>
    private lateinit var drawerToggle: ActionBarDrawerToggle


    private lateinit var menuAnimHamToCross: AnimatedVectorDrawable
    private lateinit var menuAnimCrossToHam: AnimatedVectorDrawable
    private lateinit  var startBtnMenu: VectorDrawable


    private lateinit var finishBtnMenu: VectorDrawable


    private var mMenuFlag = true

    private var isThemChanged = false

    private lateinit var operationLine: TextView
    private lateinit var resultLine: TextView

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

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseThemeViewModel::class.java)

    }

    fun upDateOperationAndResultLines(pair: Pair<String,String>)
    {
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

        zeroButton = view.findViewById(R.id.btn_zero)
        oneButton = view.findViewById(R.id.btn_one)
        twoButton = view.findViewById(R.id.btn_two)
        threeButton = view.findViewById(R.id.btn_three)
        fourButton = view.findViewById(R.id.btn_four)
        fiveButton = view.findViewById(R.id.btn_five)
        sixButton = view.findViewById(R.id.btn_six)
        sevenButton = view.findViewById(R.id.btn_six)
        eightButton = view.findViewById(R.id.btn_eight)
        nineButton = view.findViewById(R.id.btn_nine)
        dotButton = view.findViewById(R.id.btn_dot)

        backSpButton = view.findViewById(R.id.btn_back)
        resetButton = view.findViewById(R.id.btn_reset)

        percentageButton = view.findViewById(R.id.btn_percentage)
        divideButton = view.findViewById(R.id.btn_divide)
        multiplyButton = view.findViewById(R.id.btn_multiple)
        minusButton = view.findViewById(R.id.btn_minus)
        plusButton = view.findViewById(R.id.btn_plus)
        equalButton = view.findViewById(R.id.btn_equal)

        /*drawerToggle.run {
            isDrawerIndicatorEnabled = true
            setHomeAsUpIndicator(R.drawable.start_btn_menu)
            drawerLayout.addDrawerListener(this)
        }

*/

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

        percentageButton.setOnClickListener(this)
        divideButton.setOnClickListener(this)
        multiplyButton.setOnClickListener(this)
        minusButton.setOnClickListener(this)
        plusButton.setOnClickListener(this)
        equalButton.setOnClickListener(this)

    }
    override fun onClick(view: View?) {

        when (view) {
            zeroButton ->  upDateOperationAndResultLines(viewModel.onNumberClick(zeroButton.toString()))
            oneButton -> upDateOperationAndResultLines(viewModel.onNumberClick(oneButton.toString()))
            twoButton -> upDateOperationAndResultLines(viewModel.onNumberClick(twoButton.toString()))
            threeButton -> upDateOperationAndResultLines(viewModel.onNumberClick(threeButton.toString()))
            fourButton -> upDateOperationAndResultLines(viewModel.onNumberClick(fourButton.toString()))
            fiveButton -> upDateOperationAndResultLines(viewModel.onNumberClick(fiveButton.toString()))
            sixButton -> upDateOperationAndResultLines(viewModel.onNumberClick(sixButton.toString()))
            sevenButton -> upDateOperationAndResultLines(viewModel.onNumberClick(sevenButton.toString()))
            eightButton -> upDateOperationAndResultLines(viewModel.onNumberClick(eightButton.toString()))
            nineButton -> upDateOperationAndResultLines(viewModel.onNumberClick(nineButton.toString()))
            dotButton -> upDateOperationAndResultLines(viewModel.onNumberClick(dotButton.toString()))

            backSpButton -> upDateOperationAndResultLines(viewModel.backspaceClick())
            resetButton -> upDateOperationAndResultLines(viewModel.resetClick())

            percentageButton -> upDateOperationAndResultLines(viewModel.onOperationClick(percentageButton.toString()))
            divideButton -> upDateOperationAndResultLines(viewModel.onOperationClick(divideButton.toString()))
            multiplyButton -> upDateOperationAndResultLines(viewModel.onOperationClick(multiplyButton.toString()))
            minusButton -> upDateOperationAndResultLines(viewModel.onOperationClick(minusButton.toString()))
            plusButton -> upDateOperationAndResultLines(viewModel.onOperationClick(plusButton.toString()))
            equalButton -> upDateOperationAndResultLines(viewModel.onOperationClick(equalButton.toString()))



        }
    }




 }