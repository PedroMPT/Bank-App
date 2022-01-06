package com.example.sanbox_test_api.views

import com.example.sanbox_test_api.models.Payment

/**
 * @interface PaymentListView
 **/
interface PaymentListView {
    fun onReadyPostPayment(payment: Payment?, message: String)
    fun onReadyPostMBPayment(payment: Payment?, message: String)
}