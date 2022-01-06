package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Bank

/**
 * @interface BankListView
 **/
interface BankListView {
    fun onReadyGetBanks(banks: ArrayList<Bank>?, message: String)
}