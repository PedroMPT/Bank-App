package com.example.sanbox_test_api.models

import com.example.sanbox_test_api.utils.JSONConvertable
import com.google.gson.annotations.SerializedName

/**
 * @class Account
 **/
data class Account(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("iban")
    var iban: String = "",
    @SerializedName("bban")
    var bban: String = "",
    @SerializedName("pan")
    var pan: String = "",
    @SerializedName("maskedPan")
    var maskedPan: String = "",
    @SerializedName("msisdn")
    var msisdn: String = "",
    @SerializedName("currency")
    var currency: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("accountType")
    var accountType: String = "",
    @SerializedName("cashAccountType")
    var cashAccountType: String = "",
    @SerializedName("bic")
    var bic: String = ""
) : JSONConvertable