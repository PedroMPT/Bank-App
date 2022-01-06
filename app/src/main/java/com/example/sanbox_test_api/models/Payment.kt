package com.example.sanbox_test_api.models

import com.example.sanbox_test_api.utils.JSONConvertable
import com.google.gson.annotations.SerializedName

/**
 * @class TransactionFees
 **/
data class TransactionFees (
    @SerializedName("currency")
    var currency: String = "",
    @SerializedName("content")
    var content: String = ""
): JSONConvertable

/**
 * @class CreditorAddress
 **/
data class CreditorAddress (
    @SerializedName("street")
    var street: String = "",
    @SerializedName("buildingNumber")
    var buildingNumber: String = "",
    @SerializedName("city")
    var city: String = "",
    @SerializedName("postalCode")
    var postalCode: String = "",
    @SerializedName("country")
    var country: String = ""
): JSONConvertable

/**
 * @class CreditorAccount
 **/
data class CreditorAccount(
    @SerializedName("iban")
    var iban: String = "",
    @SerializedName("currency")
    var currency: String = ""
): JSONConvertable

/**
 * @class Payment
 **/
data class Payment (
    @SerializedName("transactionStatus")
    var transactionStatus: String = "",
    @SerializedName("paymentId")
    var paymentId: String = "",
    @SerializedName("psuMessage")
    var psuMessage: String = "",
    @SerializedName("transactionFees")
    var transactionFees: TransactionFees,
    @SerializedName("transactionFeeIndicator")
    var transactionFeeIndicator: String = ""
): JSONConvertable