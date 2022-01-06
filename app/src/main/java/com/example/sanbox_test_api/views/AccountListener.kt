package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Account

/**
 * @interface AccountListener
 **/
interface AccountListener {
    fun onCardClickListener(account: Account)
}