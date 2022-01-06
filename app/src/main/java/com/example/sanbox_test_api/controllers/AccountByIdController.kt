package com.example.sanbox_test_api.controllers

import android.content.Context
import android.util.Log
import com.example.sanbox_test_api.api.ApiClient
import com.example.sanbox_test_api.views.AccountByIdListView

class AccountByIdController (private val ctx: Context,
                             private var accountByIdListView: AccountByIdListView) {

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the account id interface view
     * to later be displayed on the activity
     * @param aspspCode
     * @param accountId
     * @param withBalance
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     **/
    fun getAccountById(aspspCode: String,
                       accountId: String,
                       withBalance: String,
                       psuInvolved: String,
                       consentId: String,
                       signature: String,
                       digest: String,
                       certificate: String,
                       date: String) {
        ApiClient(ctx)
            .getAccountById(aspspCode,
                accountId,
                withBalance,
                psuInvolved,
                consentId,
                signature,
                digest,
                certificate,
                date) {
                    account,
                    message ->
                Log.d("GetAccountById Message", message)
                accountByIdListView.onReadyGetAccountById(account, message)
            }
    }
}