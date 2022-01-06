package com.example.sanbox_test_api.controllers

import android.content.Context
import android.util.Log
import com.example.sanbox_test_api.api.ApiClient
import com.example.sanbox_test_api.views.*

class BalanceController(private val ctx: Context,
                        private var balanceView: BalanceListView) {

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the balances interface view
     * to later be displayed on the activity
     * @param aspspCode
     * @param accountId
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     **/
    fun getBalances(aspspCode: String,
                    accountId: String,
                    psuInvolved: String,
                    consentId: String,
                    signature: String,
                    digest: String,
                    certificate: String,
                    date: String) {
        ApiClient(ctx)
            .getBalances(aspspCode,
                accountId,
                psuInvolved,
                consentId,
                signature,
                digest,
                certificate,
                date) {
                    balances,
                    message ->
                Log.d("GetBalances Message", message)
                balanceView.onReadyGetBalances(balances, message)
            }
    }
}