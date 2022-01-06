package com.example.sanbox_test_api.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.controllers.AccountController
import com.example.sanbox_test_api.controllers.BalanceController
import com.example.sanbox_test_api.controllers.BankController
import com.example.sanbox_test_api.controllers.TransactionController
import com.example.sanbox_test_api.models.Account
import com.example.sanbox_test_api.models.Balance
import com.example.sanbox_test_api.models.Bank
import com.example.sanbox_test_api.models.Transaction
import com.example.sanbox_test_api.utils.FormattedDate
import com.example.sanbox_test_api.utils.JSONConvertable
import com.example.sanbox_test_api.views.AccountListView
import com.example.sanbox_test_api.views.BalanceListView
import com.example.sanbox_test_api.views.BankListView
import com.example.sanbox_test_api.views.TransactionListView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddAccount : AppCompatActivity(),
    AccountListView,
    BankListView,
    BalanceListView,
    TransactionListView,
    JSONConvertable {

    /**
     * BankController
     **/
    private val bankController: BankController by lazy {
        BankController(this, this)
    }

    /**
     * AccountController
     **/
    private val accountController: AccountController by lazy {
        AccountController(this, this)
    }

    /**
     * BalanceController
     **/
    private val balanceController: BalanceController by lazy {
        BalanceController(this,this)
    }

    /**
     * TransactionController
     **/
    private val transactionController: TransactionController by lazy {
        TransactionController(this, this)
    }

    /**
     * Array of banks
     **/
    private val banksList: ArrayList<Bank> = arrayListOf()

    /**
     * Array of accounts
     **/
    private var accountsList: ArrayList<Account> = arrayListOf()

    /**
     * Array of balances
     **/
    private val balanceList: ArrayList<Balance> = arrayListOf()

    /**
     * Array of transactions
     **/
    private val transactionsList: ArrayList<Transaction> = arrayListOf()

    /**
     * UI variables
     **/
    private lateinit var dropdown: TextInputLayout
    lateinit var id: TextInputLayout

    /**
     * Firebase reference
     **/
    private lateinit var database: DatabaseReference

    /**
     * API timeout handler
     **/
    private val TIME_OUT:Long = 3000

    /**
     * On create lifecycle
     * @param savedInstanceState
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        bankController.getBanks()

        val addAccount = findViewById<Button>(R.id.add_btn)
        id = findViewById(R.id.outlinedTextField)

        addAccount.setOnClickListener {
            val iban = id.editText?.text.toString().trim()
            if  (dropdown.editText?.text.toString().trim().isEmpty() || iban.isEmpty()) {
                Toast.makeText(this,
                    "Introduza valores válidos", Toast.LENGTH_SHORT).show()
            } else if (!iban.startsWith("PT50")) {
                Toast.makeText(this,
                    "O iban introduzido não é válido", Toast.LENGTH_SHORT).show()
            } else {
                setAccount()
            }

        }
    }

    /**
     * Balance API call
     **/
    private fun setBalance() {
        val asps = getBank()
        balanceController.getBalances(
            asps.elementAt(0),
            accountsList.elementAt(0).id,
            "true",
            "1",
            "1",
            "",
            "1",
            "TUE, 17 MAY 2020 11:28:17 GMT")
    }

    /**
     * Accounts API call
     **/
    private fun setAccount() {
        val asps = getBank()
        accountController.getAccounts(
            asps.elementAt(0),
            "true",
            "true",
            "1",
            "1",
            "",
            "1",
            FormattedDate.formatDate()!!)
    }

    /**
     * Transactions API call
     * @param accountId
     **/
    private fun setTransactions(accountId: String) {
        val asps = getBank()
        transactionController.getTransactions(
            asps.elementAt(0),
            accountId,
            "true",
            "TUE, 05 MAY 2020 11:28:17 GMT",
            FormattedDate.formatDate()!!,
            "true",
            "71525dacac1763b812af4e83af61853",
            "both",
            "false",
            "1",
            "1",
            "",
            "1",
            FormattedDate.formatDate()!!
        )
    }

    /**
     * Banks API response
     * List banks on the ui
     * @param banks
     * @param message
     **/
    override fun onReadyGetBanks(banks: ArrayList<Bank>?, message: String) {
        if (banks !== null) {
            banksList.addAll(banks)
            val autoCompleteList = banksList.map { bank -> bank.name }
            val adapter =
                ArrayAdapter(this, R.layout.bank_list, autoCompleteList)
            dropdown = findViewById(R.id.dropdown)
            (dropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Accounts API response
     * Method to save all of the accounts info in firebase
     * @param accounts
     * @param message
     **/
    override fun onReadyGetAccounts(accounts: ArrayList<Account>?, message: String) {
       if (accounts != null) {
           accountsList.addAll(accounts)
           setBalance()
           Toast.makeText(this@AddAccount,
               "Aguarde enquanto preparamos a sua conta bancária", Toast.LENGTH_LONG).show()
           Handler().postDelayed({
               database = FirebaseDatabase.getInstance().reference
               val iban = id.editText?.text.toString()
               val filteredAccounts = accountsList
                   .filter { account -> account.iban == iban } as ArrayList<Account>
               val amount = balanceList
                   .map { balance -> balance.expected.amount }
               filteredAccounts
                   .map { account -> account.currency = amount
                       .elementAt(0).content }
               filteredAccounts
                   .map { account -> account.msisdn = dropdown
                       .editText?.text.toString() }
               filteredAccounts
                   .map { account -> account.pan = banksList
                       .elementAt(0).aspsCode }
               val newAccount = filteredAccounts
                   .elementAt(0)
               val accountId = newAccount.id
               setTransactions(accountId)

               Handler().postDelayed({
                   val user = FirebaseAuth.getInstance().currentUser
                   user?.let {
                       val uid = user.uid
                       database.child("accounts").child(uid)
                           .push().setValue(newAccount)
                   }
                   val accountById = FirebaseDatabase.getInstance()
                       .getReference("accounts")
                   accountById.let {
                       database.child("transactions").child(accountId)
                           .push().setValue(transactionsList)
                   }
                    this@AddAccount?.let{
                       val intent = Intent (it, MainActivity::class.java)
                       intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                               or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                       it.startActivity(intent)
                       finish()
                   }
                   Toast.makeText(this@AddAccount,
                       "Conta adicionada com sucesso", Toast.LENGTH_LONG).show()
               }, TIME_OUT)
           }, TIME_OUT)


       } else {
           Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
       }
    }

    /**
     * Balances API response
     * Method to save all of the accounts info in firebase
     * @param balances
     * @param message
     **/
    override fun onReadyGetBalances(balances: ArrayList<Balance>?, message: String) {
        if (balances != null) {
            balanceList.addAll(balances)
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Get selected bank
     **/
    private fun getBank(): List<String> {
        val bankASPSCode = banksList
            .filter { bank -> bank.name == dropdown.editText?.text.toString()}
        return bankASPSCode.map { bank -> bank.aspsCode }
    }

    /**
     * Transactions API response
     * @param transactions
     * @param message
     **/
    override fun onReadyGetTransactions(
        transactions: HashMap<String, ArrayList<Transaction>>?,
        message: String
    ) {
        if (transactions != null) {
            transactions.map { transaction ->
                transactionsList.addAll(transaction.value) }
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}
