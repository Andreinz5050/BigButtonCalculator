package com.example.android.bigbuttoncalculator


import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.activity_light_fragment.*
import javax.inject.Inject

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BaseThemeView : Fragment(), View.OnClickListener {

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

    private lateinit var zeroButton: ImageView
    private lateinit var oneButton: ImageView
    private lateinit var twoButton: ImageView
    private lateinit var threeButton: ImageView
    private lateinit var fourButton: ImageView
    private lateinit var fiveButton: ImageView
    private lateinit var sixButton: ImageView
    private lateinit var sevenButton: ImageView
    private lateinit var eightButton: ImageView
    private lateinit var nineButton: ImageView
    private lateinit var dotButton: ImageView

    private lateinit var backSpButton: ImageView
    private lateinit var resetButton: ImageView

    private lateinit var percentageButton: ImageView
    private lateinit var divideButton: ImageView
    private lateinit var multiplyButton: ImageView
    private lateinit var minusButton: ImageView
    private lateinit var plusButton: ImageView
    private lateinit var equalButton: ImageView




    private lateinit var viewModel: BaseThemeModel




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_light_fragment, container, false)
        initializeFields(view)
        setUp()


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseThemeModel::class.java)

    }

    fun upDateOperationLine(string: String)
    {
        operationLine.text = string
    }

    fun upDateResultLine(string: String)
    {
        resultLine.text = string
    }

    private fun initializeFields(view: View) {
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

        drawerToggle.run {
            isDrawerIndicatorEnabled = true
            setHomeAsUpIndicator(R.drawable.start_btn_menu)
            drawerLayout.addDrawerListener(this)
        }



    }




    private fun setUp() {
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
            zeroButton -> viewModel.onNumberClick(view)
            oneButton -> viewModel.onNumberClick(view)
            twoButton -> viewModel.onNumberClick(view)
            threeButton -> viewModel.onNumberClick(view)
            fourButton -> viewModel.onNumberClick(view)
            fiveButton -> viewModel.onNumberClick(view)
            sixButton -> viewModel.onNumberClick(view)
            sevenButton -> viewModel.onNumberClick(view)
            eightButton -> viewModel.onNumberClick(view)
            nineButton -> viewModel.onNumberClick(view)
            dotButton -> viewModel.onNumberClick(view)

            backSpButton -> viewModel.backspaceClick(view)
            resetButton -> viewModel.resetClick(view)

            percentageButton -> viewModel.onOperationClick(view)
            divideButton -> viewModel.onOperationClick(view)
            multiplyButton -> viewModel.onOperationClick(view)
            minusButton -> viewModel.onOperationClick(view)
            plusButton -> viewModel.onOperationClick(view)
            equalButton -> viewModel.onOperationClick(view)



        }
    }




    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("OPERATION")
        valueOne = savedInstanceState.getString("VALUEONE")

        resultLine.text = savedInstanceState.getString("RESULTLINE")
        operationLine.text = savedInstanceState.getString("OPERATIONLINE")
    }















}