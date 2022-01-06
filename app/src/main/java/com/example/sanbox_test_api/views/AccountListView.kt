package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Account

/**
 * @interface AccountListView
 **/
interface AccountListView {

    fun onReadyGetAccounts(accounts: ArrayList<Account>?, message: String)
}