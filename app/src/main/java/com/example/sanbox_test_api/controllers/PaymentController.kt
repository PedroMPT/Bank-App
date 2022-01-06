package com.example.sanbox_test_api.controllers

import android.content.Context
import android.util.Log
import com.example.sanbox_test_api.api.ApiClient
import com.example.sanbox_test_api.models.CreditorAccount
import com.example.sanbox_test_api.models.CreditorAddress
import com.example.sanbox_test_api.models.TransactionFees
import com.example.sanbox_test_api.views.PaymentListView

class PaymentController(private val ctx: Context,
                        private var paymentView: PaymentListView) {

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the payments interface view
     * to later be displayed on the activity
     * @param aspspCode
     * @param paymentProduct
     * @param tppRedirectPreferred
     * @param psuId
     * @param psuIdType
     * @param psuCorporateId
     * @param psuCorporateIdType
     * @param psuConsentId
     * @param psuAgent
     * @param psuIpAddress
     * @param psuGeoLocation
     * @param tppRedirectUri
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     * @param endToEndIdentification
     * @param debtorAccount
     * @param instructedAmount
     * @param creditorAccount
     * @param creditorAgent
     * @param creditorName
     * @param creditorAddress
     * @param remittanceInformationUnstructured
     **/
    fun postPayment(aspspCode: String,
                    paymentProduct: String,
                    tppRedirectPreferred: String,
                    psuId: String,
                    psuIdType: String,
                    psuCorporateId: String,
                    psuCorporateIdType: String,
                    psuConsentId: String,
                    psuAgent: String,
                    psuIpAddress: String,
                    psuGeoLocation: String,
                    tppRedirectUri: String,
                    signature: String,
                    digest: String,
                    certificate: String,
                    date: String,
                    endToEndIdentification: String,
                    debtorAccount: CreditorAccount,
                    instructedAmount: TransactionFees,
                    creditorAccount: CreditorAccount,
                    creditorAgent: String,
                    creditorName: String,
                    creditorAddress: CreditorAddress,
                    remittanceInformationUnstructured: String,
                    requestedExecutionDate: String) {
        ApiClient(ctx).postPayment(
            aspspCode,
            paymentProduct,
            tppRedirectPreferred,
            psuId,
            psuIdType,
            psuCorporateId,
            psuCorporateIdType,
            psuConsentId,
            psuAgent,
            psuIpAddress,
            psuGeoLocation,
            tppRedirectUri,
            signature,
            digest,
            certificate,
            date,
            endToEndIdentification,
            debtorAccount,
            instructedAmount,
            creditorAccount,
            creditorAgent,
            creditorName,
            creditorAddress,
            remittanceInformationUnstructured,
            requestedExecutionDate
        ) {payment,
           message ->
            Log.d("PostPayment Message", message)
            paymentView.onReadyPostPayment(payment, message)
        }
    }

    /**
     * Method that'll manage the communication between the activity and the api client
     * After completion the values will be set on the transactions interface view
     * to later be displayed on the activity
     * @param aspspCode
     * @param mbPaymentType
     * @param servicePaymentName
     * @param tppRedirectPreferred
     * @param tppRedirectUri
     * @param psuGeoLocation
     * @param psuAgent
     * @param psuIpAddress
     * @param psuId
     * @param psuConsentId
     * @param psuIdType
     * @param date
     * @param signature
     * @param digest
     * @param certificate
     * @param instructedAmount
     * @param multibancoPaymentReference
     * @param multibancoPaymentEntity
     * @param taxpayerIdentificationNumber
     * @param debtorAccount
     * @param requestedExecutionDate
     * @param requestedExecutionTime
     **/
    fun postMBPayment(aspspCode: String,
                      mbPaymentType: String,
                      servicePaymentName: String,
                      tppRedirectPreferred: String,
                      tppRedirectUri: String,
                      psuGeoLocation: String,
                      psuAgent: String,
                      psuIpAddress: String,
                      psuId: String,
                      psuCorporateId: String,
                      psuCorporateIdType: String,
                      psuConsentId: String,
                      psuIdType: String,
                      date: String,
                      signature: String,
                      digest: String,
                      certificate: String,
                      instructedAmount: TransactionFees,
                      multibancoPaymentReference: String,
                      multibancoPaymentEntity: String,
                      taxpayerIdentificationNumber: String,
                      debtorAccount: CreditorAccount,
                      requestedExecutionDate: String,
                      requestedExecutionTime: String) {
        ApiClient(ctx).postMBPayment(
            aspspCode,
            mbPaymentType,
            servicePaymentName,
            tppRedirectPreferred,
            tppRedirectUri,
            psuGeoLocation,
            psuAgent,
            psuIpAddress,
            psuId,
            psuCorporateId,
            psuCorporateIdType,
            psuConsentId,
            psuIdType,
            date,
            signature,
            digest,
            certificate,
            instructedAmount,
            multibancoPaymentReference,
            multibancoPaymentEntity,
            taxpayerIdentificationNumber,
            debtorAccount,
            requestedExecutionDate,
            requestedExecutionTime
        ) {payment,
           message ->
            Log.d("PostMBPayment Message", message)
            paymentView.onReadyPostMBPayment(payment, message)
        }
    }
}