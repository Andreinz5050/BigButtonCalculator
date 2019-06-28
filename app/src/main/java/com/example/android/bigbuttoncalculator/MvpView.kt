package com.example.android.bigbuttoncalculator

import android.support.annotation.StringRes
import androidx.annotation.StringRes

interface MvpView {
    fun showLoading()
    fun hideLoading()
    fun onError(@StringRes resId: Int)
    fun onError(message: String?)
    fun isNetworkConnected(): Boolean
    fun hideKeyBoard()

}