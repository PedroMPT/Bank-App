package com.example.sanbox_test_api.models

import com.example.sanbox_test_api.utils.JSONConvertable
import com.google.gson.annotations.SerializedName

/**
 * @class ApiList
 **/
data class ApiList(
    @SerializedName("consents")
    var consents:ArrayList<String>,
    @SerializedName("payments")
    var payments:ArrayList<String>,
    @SerializedName("accounts")
    var accounts:ArrayList<String>,
    @SerializedName("balances")
    var balances:ArrayList<String>,
    @SerializedName("transaction")
    var transaction:ArrayList<String>,
    @SerializedName("funds-confirmations")
    var fundsConfirmation:ArrayList<String>
): JSONConvertable

/**
 * @class Bank
 **/
data class Bank(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("bic")
    var bic: String = "",
    @SerializedName("bank-code")
    var bankCode: String = "",
    @SerializedName("aspsp-cde")
    var aspsCode: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("logoLocation")
    var logoLocation: String = "",
    @SerializedName("api-list")
    var apiList: ArrayList<ApiList>
) : JSONConvertable