package com.example.sanbox_test_api.api

import com.android.volley.Request
import com.example.sanbox_test_api.models.CreditorAccount
import com.example.sanbox_test_api.models.CreditorAddress
import com.example.sanbox_test_api.models.TransactionFees
import kotlin.collections.HashMap

sealed class ApiRoute {

    /**
     * Time out for api requests
     **/
    val timeOut: Int
        get() {
            return 3000
        }

    /**
     * Base url for all apis
     **/
    val baseUrl: String
        get() {
            return "https://site1.sibsapimarket.com:8445/sibs/apimarket-sb"
        }

    /**
     * Data class for accounts that includes api params, header's and body if necessary
     * @param aspspCode
     * @param withBalance
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param certificate
     * @param date
     **/
    data class GetAccount(var aspspCode: String,
                          var withBalance: String,
                          var psuInvolved: String,
                          var consentId: String,
                          var signature: String,
                          var digest: String,
                          var certificate: String,
                          var date: String) : ApiRoute()

    /**
     * Data class for accounts by id that includes api params, header's and body if necessary
     * @param aspspCode
     * @param accountId
     * @param withBalance
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param certificate
     * @param date
     **/
    data class GetAccountById(var aspspCode: String,
                              var accountId: String,
                              var withBalance: String,
                              var psuInvolved: String,
                              var consentId: String,
                              var signature: String,
                              var digest: String,
                              var certificate: String,
                              var date: String) : ApiRoute()

    /**
     * Class for get banks
     **/
    class GetBanks : ApiRoute()

    /**
     * Data class for balances that includes api params, header's and body if necessary
     * @param aspspCode
     * @param accountId
     * @param psuInvolved
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     **/
    data class GetBalances(var aspspCode: String,
                           var accountId: String,
                           var psuInvolved: String,
                           var consentId: String,
                           var signature: String,
                           var digest: String,
                           var certificate: String,
                           var date: String) : ApiRoute()

    /**
     * Data class for transactions that includes api params, header's and body if necessary
     * @param aspspCode
     * @param accountId
     * @param transactionId
     * @param dateFrom
     * @param dateTo
     * @param withBalance
     * @param psuInvolved
     * @param bookingStatus
     * @param deltaList
     * @param consentId
     * @param signature
     * @param digest
     * @param certificate
     * @param date
     **/
    data class GetTransactions(var aspspCode: String,
                           var accountId: String,
                           var transactionId: String,
                           var dateFrom: String,
                           var dateTo: String,
                           var withBalance: String,
                           var psuInvolved: String,
                           var bookingStatus: String,
                           var deltaList: String,
                           var consentId: String,
                           var signature: String,
                           var digest: String,
                           var certificate: String,
                           var date: String) : ApiRoute()

    /**
     * Data class for payments that includes api params, header's and body if necessary
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
    data class PostPayment(var aspspCode: String,
                           var paymentProduct: String,
                           var tppRedirectPreferred: String,
                           var psuId: String,
                           var psuIdType: String,
                           var psuCorporateId: String,
                           var psuCorporateIdType: String,
                           var psuConsentId: String,
                           var psuAgent: String,
                           var psuIpAddress: String,
                           var psuGeoLocation: String,
                           var tppRedirectUri: String,
                           var signature: String,
                           var digest: String,
                           var certificate: String,
                           var date: String,
                           var endToEndIdentification: String,
                           var debtorAccount: CreditorAccount,
                           var instructedAmount: TransactionFees,
                           var creditorAccount: CreditorAccount,
                           var creditorAgent: String,
                           var creditorName: String,
                           var creditorAddress: CreditorAddress,
                           var remittanceInformationUnstructured: String,
                           var requestedExecutionDate: String) : ApiRoute()

    /**
     * Data class for MB payments that includes api params, header's and body if necessary
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
     **/
    data class PostMBPayment(var aspspCode: String,
                             var mbPaymentType: String,
                             var servicePaymentName: String,
                             var tppRedirectPreferred: String,
                             var tppRedirectUri: String,
                             var psuGeoLocation: String,
                             var psuAgent: String,
                             var psuIpAddress: String,
                             var psuId: String,
                             var psuCorporateId: String,
                             var psuCorporateIdType: String,
                             var psuConsentId: String,
                             var psuIdType: String,
                             var date: String,
                             var signature: String,
                             var digest: String,
                             var certificate: String,
                             var instructedAmount: TransactionFees,
                             var multibancoPaymentReference: String,
                             var multibancoPaymentEntity: String,
                             var taxpayerIdentificationNumber: String,
                             var debtorAccount: CreditorAccount,
                             var requestedExecutionDate: String,
                             var requestedExecutionTime: String) : ApiRoute()

    /**
     * Http methods for each Api
     **/
    val httpMethod: Int
        get() {
            return when (this) {
                is GetAccount -> Request.Method.GET
                is GetBanks -> Request.Method.GET
                is GetBalances -> Request.Method.GET
                is GetAccountById -> Request.Method.GET
                is GetTransactions -> Request.Method.GET
                is PostPayment -> Request.Method.POST
                is PostMBPayment -> Request.Method.POST
            }
        }

    /**
     * Params necessary for each api call
     **/
    val params: HashMap<String, String>
        get() {
            return when (this) {
                is GetAccount -> {
                    hashMapOf(
                        Pair("aspsp-cde", this.aspspCode),
                        Pair("withBalance",this.withBalance),
                        Pair("psuInvolved", this.psuInvolved))
                }
                is GetBanks -> {
                    hashMapOf()
                }

                is GetBalances -> {
                    hashMapOf(
                        Pair("aspsp-cde", this.aspspCode),
                        Pair("account-id",this.accountId),
                        Pair("psuInvolved", this.psuInvolved))
                }

                is GetAccountById -> {
                    hashMapOf(
                        Pair("aspsp-cde", this.aspspCode),
                        Pair("account-id",this.accountId),
                        Pair("withBalance",this.withBalance),
                        Pair("psuInvolved", this.psuInvolved))
                }

                is GetTransactions -> {
                    hashMapOf(
                        Pair("aspsp-cde", this.aspspCode),
                        Pair("account-id",this.accountId),
                        Pair("dateFrom",this.dateFrom),
                        Pair("dateTo",this.dateTo),
                        Pair("transactionId",this.transactionId),
                        Pair("withBalance",this.withBalance),
                        Pair("bookingStatus",this.bookingStatus),
                        Pair("deltaList",this.deltaList),
                        Pair("psuInvolved", this.psuInvolved))
                }

                is PostPayment -> {
                    hashMapOf(
                        Pair("aspsp-cde", this.aspspCode),
                        Pair("payment-product", this.paymentProduct),
                        Pair("tppRedirectPreferred", this.tppRedirectPreferred))
                }

                is PostMBPayment -> {
                    hashMapOf(
                        Pair("aspsp-cde", this.aspspCode),
                        Pair("multibanco-payment-type", this.mbPaymentType),
                        Pair("service-payment-name", this.servicePaymentName),
                        Pair("tppRedirectPreferred", this.tppRedirectUri))
                }
            }
        }

    /**
     * Header's necessary for each api call
     **/
    val headers: HashMap<String, String>
        get() {
            val map: HashMap<String, String> = hashMapOf()
            map["accept"] = "application/json"
            map["x-ibm-client-id"] = "b782a1d7-1501-4cf6-998f-049de83bf8a7"
            map["tpp-transaction-id"] = "ceae5ddb2325457bac80b43baefaf558"
            map["tpp-request-id"] = "bcd4aad6fcc246419485a015f4cb6996"
            return when (this) {
                is GetAccount -> {
                    map["consent-id"] = this.consentId
                    map["signature"] = this.signature
                    map["digest"] = this.digest
                    map["tpp-certificate"] = this.certificate
                    map["date"] = this.date
                    map
                }
                is GetBanks -> {
                    map
                }

                is GetBalances -> {
                    map["consent-id"] = this.consentId
                    map["signature"] = this.signature
                    map["digest"] = this.digest
                    map["tpp-certificate"] = this.certificate
                    map["date"] = this.date
                    map
                }

                is GetAccountById -> {
                    map["consent-id"] = this.consentId
                    map["signature"] = this.signature
                    map["digest"] = this.digest
                    map["tpp-certificate"] = this.certificate
                    map["date"] = this.date
                    map
                }

                is GetTransactions -> {
                    map["consent-id"] = this.consentId
                    map["signature"] = this.signature
                    map["digest"] = this.digest
                    map["tpp-certificate"] = this.certificate
                    map["date"] = this.date
                    map
                }

                is PostPayment -> {
                    map["content-type"] = "application/json"
                    map["psu-id"] = this.psuId
                    map["psu-id-type"] = this.psuIdType
                    map["psu-corporate-id"] = this.psuCorporateId
                    map["psu-corporate-id-type"] = this.psuCorporateIdType
                    map["psu-consent-id"] = this.psuConsentId
                    map["psu-agent"] = this.psuAgent
                    map["psu-ip-address"] = this.psuIpAddress
                    map["psu-geo-location"] = this.psuGeoLocation
                    map["tpp-redirect-uri"] = this.tppRedirectUri
                    map["signature"] = this.signature
                    map["digest"] = this.digest
                    map["tpp-certificate"] = this.certificate
                    map["date"] = this.date
                    map
                }

                is PostMBPayment -> {
                    map["content-type"] = "application/json"
                    map["tpp-redirect-uri"] = this.tppRedirectUri
                    map["psu-geo-location"] = this.psuGeoLocation
                    map["psu-agent"] = this.psuAgent
                    map["psu-ip-address"] = this.psuIpAddress
                    map["psu-id"] = this.psuId
                    map["psu-id-type"] = this.psuIdType
                    map["psu-corporate-id"] = this.psuCorporateId
                    map["psu-corporate-id-type"] = this.psuCorporateIdType
                    map["psu-consent-id"] = this.psuConsentId
                    map["date"] = this.date
                    map["signature"] = this.signature
                    map["digest"] = this.digest
                    map["tpp-certificate"] = this.certificate
                    map
                }
            }
        }

    /**
     * Body necessary for each api post call
     **/
    val body: HashMap<String, Any>
        get() {
            return when (this) {
                is GetAccount -> {
                    hashMapOf()
                }
                is GetBanks -> {
                    hashMapOf()
                }

                is GetBalances -> {
                    hashMapOf()
                }

                is GetAccountById -> {
                    hashMapOf()
                }

                is GetTransactions -> {
                    hashMapOf()
                }

                is PostPayment -> {
                    hashMapOf(
                        Pair("endToEndIdentification", this.endToEndIdentification),
                        Pair("debtorAccount", this.debtorAccount),
                        Pair("instructedAmount", this.instructedAmount),
                        Pair("creditorAccount", this.creditorAccount),
                        Pair("creditorAgent", this.creditorAgent),
                        Pair("creditorName", this.creditorName),
                        Pair("creditorAddress", this.creditorAddress),
                        Pair("remittanceInformationUnstructured",
                            this.remittanceInformationUnstructured),
                        Pair("requestedExecutionDate", this.requestedExecutionDate)
                    )
                }

                is PostMBPayment -> {
                    hashMapOf(
                        Pair("instructedAmount", this.instructedAmount),
                        Pair("multibancoPaymentReference", this.multibancoPaymentReference),
                        Pair("multibancoPaymentEntity", this.multibancoPaymentEntity),
                        Pair("taxpayerIdentificationNumber", this.taxpayerIdentificationNumber),
                        Pair("debtorAccount", this.debtorAccount),
                        Pair("requestedExecutionDate", this.requestedExecutionDate),
                        Pair("requestedExecutionTime", this.requestedExecutionTime)
                    )
                }
            }
        }

    /**
     * Final API url with all the necessary params
     **/
    val url: String
        get() {
            return "$baseUrl/${when (this@ApiRoute) {
                is GetAccount -> 
                    "${this.aspspCode}/v1-0-2/accounts?withBalance" +
                            "=${this.withBalance},psuInvolved=${this.psuInvolved}"
                is GetAccountById -> 
                    "${this.aspspCode}/v1-0-2/accounts/${this.accountId}?withBalance" +
                            "=${this.withBalance},psuInvolved=${this.psuInvolved}"
                is GetBanks -> 
                    "v1/available-aspsp"
                is GetBalances -> 
                    "${this.aspspCode}/v1-0-2/accounts/${this.accountId}/balances"
                is GetTransactions -> 
                    "${this.aspspCode}/v1-0-2/accounts/${this.accountId}/transactions"
                is PostPayment ->
                    "${this.aspspCode}/v1-0-2/payments/${this.paymentProduct}" +
                            "?tppRedirectPreferred=${this.tppRedirectPreferred}"
                is PostMBPayment ->
                    "${this.aspspCode}/v1-0-2/multibanco-payments/${this.mbPaymentType}" +
                            "/${this.servicePaymentName}" +
                            "?tppRedirectPreferred=${this.tppRedirectPreferred}"
            }}"
        }

}