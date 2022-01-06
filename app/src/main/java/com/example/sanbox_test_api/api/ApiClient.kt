package com.example.sanbox_test_api.api

import android.content.Context
import com.example.sanbox_test_api.models.*

class ApiClient(private val ctx: Context) {

    /**
     * This'll call the api request method to perform each request
     **/
    private val apiRequest: ApiRequest by lazy {
        ApiRequest(ctx)
    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a list of accounts
     * with the respective response
     * @param aspspCode
     * @param withBalance
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     * @param completion
     **/
    fun getAccount(
        aspspCode: String,
        withBalance: String,
        psuInvolved: String,
        consentId: String,
        signature: String,
        digest: String,
        certificate: String,
        date: String,
        completion: (accounts: ArrayList<Account>?, message: String) -> Unit
    ) {
        val route = ApiRoute.GetAccount(
            aspspCode,
            withBalance,
            psuInvolved,
            consentId,
            signature,
            digest,
            certificate,
            date
        )
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val accounts = ApiResponseService.setAccount(response)
                completion.invoke(accounts, response.message)
            } else {
                completion.invoke(arrayListOf(), response.message)
            }
        }
    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a account object
     * with the respective response
     * @param aspspCode
     * @param accountId
     * @param withBalance
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     * @param completion
     **/
    fun getAccountById(
        aspspCode: String,
        accountId: String,
        withBalance: String,
        psuInvolved: String,
        consentId: String,
        signature: String,
        digest: String,
        certificate: String,
        date: String,
        completion: (account: Account?, message: String) -> Unit
    ) {
        val route = ApiRoute.GetAccountById(
            aspspCode,
            accountId,
            withBalance,
            psuInvolved,
            consentId,
            signature,
            digest,
            certificate,
            date
        )
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val account = ApiResponseService.setAccountById(response)
                completion.invoke(account, response.message)
            } else {
                completion.invoke(null, response.message)
            }
        }
    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a list of balances
     * with the respective response
     * @param aspspCode
     * @param accountId
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     * @param completion
     **/
    fun getBalances(
        aspspCode: String,
        accountId: String,
        psuInvolved: String,
        consentId: String,
        signature: String,
        digest: String,
        certificate: String,
        date: String,
        completion: (balances: ArrayList<Balance>?, message: String) -> Unit
    ) {
        val route = ApiRoute.GetBalances(
            aspspCode,
            accountId,
            psuInvolved,
            consentId,
            signature,
            digest,
            certificate,
            date
        )
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val balances = ApiResponseService.setBalances(response)
                completion.invoke(balances, response.message)
            } else {
                completion.invoke(arrayListOf(), response.message)
            }
        }
    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a hash map of transactions
     * with the respective response
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
     * @param completion
     **/
    fun getTransactions(
        aspspCode: String,
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
        date: String,
        completion: (transactions: HashMap<String, ArrayList<Transaction>>?,
                     message: String) -> Unit
    ) {
        val route = ApiRoute.GetTransactions(
            aspspCode,
            accountId,
            psuInvolved,
            dateFrom,
            dateTo,
            transactionId,
            withBalance,
            bookingStatus,
            deltaList,
            consentId,
            signature,
            digest,
            certificate,
            date

        )
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val transactions =
                    ApiResponseService.setTransactions(response)
                completion.invoke(transactions, response.message)
            } else {
                completion.invoke(hashMapOf(), response.message)
            }
        }
    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a list of banks
     * with the respective response
     * @param completion
     **/
    fun getBanks(completion: (banks: ArrayList<Bank>?, message: String) -> Unit) {
        val route = ApiRoute.GetBanks()
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val banks = ApiResponseService.setBanks(response)
                completion.invoke(banks, response.message)
            } else {
                completion.invoke(arrayListOf(), response.message)
            }
        }
    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a hash map of payments
     * with the respective response
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
     * @param creditorAccount
     * @param instructedAmount
     * @param creditorAgent
     * @param creditorName
     * @param creditorAddress
     * @param remittanceInformationUnstructured
     * @param completion
     **/
    fun postPayment(
        aspspCode: String,
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
        requestedExecutionDate: String,
        completion: (payment: Payment?, message: String)
        -> Unit
    ) {
        val route = ApiRoute.PostPayment(
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
        )
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val payment = ApiResponseService.setPayment(response)
                completion.invoke(payment, response.message)
            } else {
                completion.invoke(null, response.message)
            }
        }

    }

    /**
     * Method that'll receive the values from the account controller to perform the api request
     * The api service will pass the response json to a hash map of mb payments
     * with the respective response
     * @param aspspCode
     * @param mbPaymentType
     * @param servicePaymentName
     * @param tppRedirectPreferred
     * @param tppRedirectUri
     * @param psuGeoLocation
     * @param psuAgent
     * @param psuIpAddress
     * @param psuId
     * @param psuCorporateId
     * @param psuCorporateIdType
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
     * @param completion
     **/
    fun postMBPayment(
        aspspCode: String,
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
        requestedExecutionTime: String,
        completion: (payment: Payment?, message: String)
        -> Unit
    ) {
        val route = ApiRoute.PostMBPayment(
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
        )
        this.apiRequest.performRequest(route) { success, response ->
            if (success) {
                val payment = ApiResponseService.setPayment(response)
                completion.invoke(payment, response.message)
            } else {
                completion.invoke(null, response.message)
            }
        }

    }

}