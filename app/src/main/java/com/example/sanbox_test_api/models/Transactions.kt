package com.example.sanbox_test_api.models

import com.example.sanbox_test_api.utils.JSONConvertable
import com.google.gson.annotations.SerializedName

/**
 * @class TransactionAccount
 **/
data class TransactionAccount(
    @SerializedName("iban")
    var iban: String,
    @SerializedName("bban")
    var bban: String,
    @SerializedName("pan")
    var pan: String,
    @SerializedName("maskedPan")
    var maskedPan: String,
    @SerializedName("msisdn")
    var msisdn: String,
    @SerializedName("currency")
    var currency: String
): JSONConvertable

/**
 * @class Transaction
 **/
data class Transaction(
    @SerializedName("transactionId")
    var transactionId: String = "",
    @SerializedName("endToEndId")
    var endToEndId: String = "",
    @SerializedName("mandateId")
    var mandateId: String = "",
    @SerializedName("creditorId")
    var creditorId: String = "",
    @SerializedName("bookingDate")
    var bookingDate: String = "",
    @SerializedName("valueDate")
    var valueDate: String = "",
    @SerializedName("amount")
    var amount: Amount?,
    @SerializedName("creditorName")
    var creditorName: String = "",
    @SerializedName("creditorAccount")
    var creditorAccount: TransactionAccount?,
    @SerializedName("ultimateCreditor")
    var ultimateCreditor: String = "",
    @SerializedName("debtorName")
    var debtorName: String = "",
    @SerializedName("debtorAccount")
    var debtorAccount: TransactionAccount?,
    @SerializedName("ultimateDebtor")
    var ultimateDebtor: String,
    @SerializedName("remittanceInformationUnstructured")
    var remittanceInformationUnstructured: String = "",
    @SerializedName("remittanceInformationStructured")
    var remittanceInformationStructured: String = "",
    @SerializedName("purposeCode")
    var purposeCode: String = ""
): JSONConvertable