package com.example.sanbox_test_api.models

import com.example.sanbox_test_api.utils.JSONConvertable
import com.google.gson.annotations.SerializedName

/**
 * @class Amount
 **/
data class Amount(
    @SerializedName("currency")
    var currency: String = "",
    @SerializedName("content")
    var content: String = ""
): JSONConvertable

/**
 * @class BalanceAmount
 **/
data class BalanceAmount(
    @SerializedName("amount")
    var amount: Amount,
    @SerializedName("lastActionDateTime")
    var lastActionDateTime: String = "",
    @SerializedName("date")
    var date: String = ""
): JSONConvertable

/**
 * @class Balance
 **/
data class Balance(
    @SerializedName("closingBooked")
    var closingBooked: BalanceAmount,
    @SerializedName("expected")
    var expected: BalanceAmount,
    @SerializedName("authorised")
    var authorised: BalanceAmount,
    @SerializedName("openingBooked")
    var openingBooked: BalanceAmount,
    @SerializedName("interimAvailable")
    var interimAvailable: BalanceAmount
) : JSONConvertable