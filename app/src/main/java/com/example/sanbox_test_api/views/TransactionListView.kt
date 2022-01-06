package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Transaction

/**
 * @interface TransactionListView
 **/
interface TransactionListView {
    fun onReadyGetTransactions(transactions: HashMap<String, ArrayList<Transaction>>?, message: String)
}