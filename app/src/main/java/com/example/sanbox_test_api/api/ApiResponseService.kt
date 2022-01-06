package com.example.sanbox_test_api.api

import com.example.sanbox_test_api.models.*
import com.example.sanbox_test_api.utils.toArrayList
import com.example.sanbox_test_api.utils.toObject


class ApiResponseService {

    companion object {

        /**
         * Method that'll pass the response to a list of accounts
         * @param response
         **/
        fun setAccount(response: ApiResponse) : ArrayList<Account> {
            val accounts: ArrayList<Account> = arrayListOf()
            val json = response.json
            if (json.has("accountList")){
                accounts.addAll(json.getJSONArray("accountList").toArrayList())
            }

            return accounts
        }

        /**
         * Method that'll pass the response to a list a account object
         * @param response
         **/
        fun setAccountById(response: ApiResponse) : Account {
            val json = response.json
            return json.getJSONObject("account").toString().toObject()
        }

        /**
         * Method that'll pass the response to a list of balances
         * @param response
         **/
        fun setBalances(response: ApiResponse) : ArrayList<Balance> {
            val balances: ArrayList<Balance> = arrayListOf()
            val json = response.json
            if (json.has("balances")){
                balances.addAll(json.getJSONArray("balances").toArrayList())
            }

            return balances
        }

        /**
         * Method that'll pass the response to a hash map of transactions
         * @param response
         **/
        fun setTransactions(response: ApiResponse) : HashMap<String, ArrayList<Transaction>> {
            val transactions: HashMap<String, ArrayList<Transaction>> = hashMapOf()
            val json = response.json
            if (json.has("transactions")){
                val transactionsObject =
                    json.getJSONObject("transactions")
                transactions["booked"] =
                    transactionsObject.getJSONArray("booked").toArrayList()
                if (transactionsObject.has("pending")) {
                    transactions["pending"] =
                        transactionsObject.getJSONArray("pending").toArrayList()
                }
            }

            return transactions
        }

        /**
         * Method that'll pass the response to a list of banks
         * @param response
         **/
        fun setBanks(response: ApiResponse) : ArrayList<Bank> {
            val banks: ArrayList<Bank> = arrayListOf()
            val json = response.json
            if (json.has("aspsp-list")){
                banks.addAll(json.getJSONArray("aspsp-list").toArrayList())
            }

            return banks
        }
        /**
         * Method that'll pass the response to a hash of payments
         * @param response
         **/
        fun setPayment(response: ApiResponse) : Payment {
            val json = response.json

            return Payment(
                json.getString("transactionStatus"),
                json.getString("paymentId"),
                json.getString("psuMessage"),
                json.getJSONObject("transactionFees").toString().toObject(),
                json.getString("transactionFeeIndicator")

            )

        }
    }
}