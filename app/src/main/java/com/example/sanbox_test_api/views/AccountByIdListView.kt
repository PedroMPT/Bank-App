package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Account

/**
 * @interface AccountByIdListView
 **/
interface AccountByIdListView {

    fun onReadyGetAccountById(account: Account?, message: String)
}