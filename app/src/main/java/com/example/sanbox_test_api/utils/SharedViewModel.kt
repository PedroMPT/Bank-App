package com.example.sanbox_test_api.utils

import com.example.sanbox_test_api.models.Account
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @class Fragment SharedViewModel
 **/
class SharedViewModel: ViewModel() {
    val selected = MutableLiveData<Account>()
    val listOfAccounts = MutableLiveData<ArrayList<Account>>()
    var totalOfAccounts = MutableLiveData<Int>()
    var totalOfBalance = MutableLiveData<Int>()

    fun setAccount(account: Account) {
        selected.value = account
    }

    fun setTotal(accounts: Int) {
        totalOfAccounts.value = accounts
    }

    fun setBalanceAmount(balance: Int) {
        totalOfBalance.value = balance
    }

    fun addAccounts(account: Account) {
        listOfAccounts.value?.add(account)
    }
}