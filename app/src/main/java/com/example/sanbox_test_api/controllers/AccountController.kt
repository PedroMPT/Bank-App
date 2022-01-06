package com.example.sanbox_test_api.controllers

import android.content.Context
import android.util.Log
import com.example.sanbox_test_api.api.ApiClient
import com.example.sanbox_test_api.views.*

class AccountController (private val ctx: Context,
                         private var accountView: AccountListView) {

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the account interface view
     * to later be displayed on the activity
     * @param aspspCode
     * @param withBalance
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     **/
    fun getAccounts(aspspCode: String,
                    withBalance: String,
                    psuInvolved: String,
                    consentId: String,
                    signature: String,
                    digest: String,
                    certificate: String,
                    date: String) {

        ApiClient(ctx)
            .getAccount(aspspCode,
                withBalance,
                psuInvolved,
                consentId,
                signature,
                digest,
                certificate,
                date) {
                    accounts,
                    message ->
                Log.d("GetAccount Message", message)
                accountView.onReadyGetAccounts(accounts, message)
            }
    }
}