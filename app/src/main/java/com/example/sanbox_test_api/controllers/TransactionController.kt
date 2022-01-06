package com.example.sanbox_test_api.controllers

import android.content.Context
import android.util.Log
import com.example.sanbox_test_api.api.ApiClient
import com.example.sanbox_test_api.views.TransactionListView

class TransactionController(private val ctx: Context,
                            private var transactionView: TransactionListView) {

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the transactions interface view
     * to later be displayed on the activity
     * @param aspspCode
     * @param accountId
     * @param psuInvolved
     * @param dateFrom
     * @param dateTo
     * @param withBalance
     * @param transactionId
     * @param bookingStatus
     * @param deltaList
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     **/
    fun getTransactions(aspspCode: String,
                        accountId: String,
                        psuInvolved: String,
                        dateFrom: String,
                        dateTo: String,
                        withBalance: String,
                        transactionId: String,
                        bookingStatus: String,
                        deltaList: String,
                        consentId: String,
                        signature: String,
                        digest: String,
                        certificate: String,
                        date: String) {
        ApiClient(ctx)
            .getTransactions(aspspCode,
                accountId,
                psuInvolved,
                dateFrom,
                dateTo,
                withBalance,
                transactionId,
                bookingStatus,
                deltaList,
                consentId,
                signature,
                digest,
                certificate,
                date
            ) {transactions,
               message ->
                Log.d("GetTransactions Message", message)
                transactionView.onReadyGetTransactions(transactions, message)
            }
    }

}