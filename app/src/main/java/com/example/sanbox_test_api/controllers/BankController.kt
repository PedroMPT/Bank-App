package com.example.sanbox_test_api.controllers

import android.content.Context
import android.util.Log
import com.example.sanbox_test_api.api.ApiClient
import com.example.sanbox_test_api.views.BankListView

class BankController(private val ctx: Context,
                     private var bankView: BankListView) {

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the banks interface view
     * to later be displayed on the activity
     **/
    fun getBanks() {
        ApiClient(ctx)
            .getBanks {banks, message ->
                Log.d("GetBanks Message", message)
                bankView.onReadyGetBanks(banks, message)
            }
    }
}