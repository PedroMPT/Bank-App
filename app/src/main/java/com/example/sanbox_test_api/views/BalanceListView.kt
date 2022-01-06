package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Balance

/**
 * @interface BalanceListView
 **/
interface BalanceListView {

    fun onReadyGetBalances(balances: ArrayList<Balance>?, message: String)
}