package com.example.sanbox_test_api.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.controllers.PaymentController
import com.example.sanbox_test_api.models.*
import com.example.sanbox_test_api.utils.FormattedDate
import com.example.sanbox_test_api.views.PaymentListView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.payment_fragment.*
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
class PaymentsFragment : Fragment(), PaymentListView {

    /**
     * Payment Controller
     **/
    private val paymentController: PaymentController by lazy {
        PaymentController(requireContext(), this)
    }

    /**
     * UI variables
     **/
    private lateinit var dropdown: TextInputLayout
    private lateinit var dropdownDate: TextInputLayout
    private lateinit var ibanText: EditText
    private lateinit var valueText: EditText
    private lateinit var paymentButton: Button

    /**
     * Accounts array
     **/
    private var arrayOfAccounts = arrayListOf<Account>()

    /**
     * Frequency array
     **/
    private val frequency : ArrayList<String> = arrayListOf()

    /**
     * Firebase authentication variable
     **/
    private var mAuth: FirebaseAuth? = null

    /**
     * User name
     **/
    private lateinit var userName: String

    /**
     * Selected bank
     **/
    private lateinit var bank: String

    /**
     * Firebase reference
     **/
    private lateinit var database: DatabaseReference


    /**
     * On create lifecycle
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     **/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.payment_fragment,
            container, false)
        getAccounts()
        mAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        ibanText = view.findViewById(R.id.iban)
        valueText = view.findViewById(R.id.value)
        paymentButton = view.findViewById(R.id.button2)

        paymentButton.setOnClickListener {
            setPayment()
        }

        setFrequency(view)
        onCheckboxClicked(view)
        return view

    }

    /**
     * Filtering the account for a specific bank
     * @return List<String>
     **/
    private fun getAccount(): List<String> {
        val iban = arrayOfAccounts
            .filter { account -> account.iban == dropdown.editText?.text.toString()}
        bank = iban.map { value -> value.pan }.elementAt(0)
        return iban.map { newAccount -> newAccount.iban }
    }

    /**
     * Gathering the information for payment api call
     **/
    private fun setPayment() {

        val iban = ibanText.text.toString().trim()
        val money = valueText.text.toString().trim()

        if (iban.isEmpty() || money.isEmpty()) {
            Toast.makeText(requireContext(),
                "Por favor indique os valores", Toast.LENGTH_LONG).show()
        } else if (!iban.startsWith("PT50") || iban.length != 25) {
            Toast.makeText(requireContext(),
                "O iban introduzido não é válido", Toast.LENGTH_LONG).show()
        } else if (!money.matches("-?\\d+(\\.\\d+)?".toRegex()) || money.toInt() < 5) {
            Toast.makeText(requireContext(),
                "O valor introduzido não é válido. Apenas valores superiores a 5€"
                , Toast.LENGTH_LONG).show()
        } else if (dropdown.editText?.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(),
                "Indique a sua conta", Toast.LENGTH_LONG).show()
        } else {
            val frequencyToSubmit = dropdownDate.editText?.text.toString()
            Log.d("FREQUENCY", frequencyToSubmit)
            val debtorAccount = CreditorAccount(
                getAccount().elementAt(0),
                "EUR"
            )

            val creditorAccount = CreditorAccount(
                iban,
                "EUR"
            )

            val transactionFees = TransactionFees (
                "EUR",
                money
            )

            val creditorAddress = CreditorAddress(
                "Rua sobe e desce",
                "210",
                "Porto",
                "4350",
                "Portugal"
            )

            val actualDate = LocalDate.now()
            val executionDate = "${actualDate.year}-" +
                    "${FormattedDate.convertDate(actualDate.monthValue)}-" +
                    "${FormattedDate.convertDate(actualDate.dayOfMonth)}"

            paymentController.postPayment(
                bank,
                "sepa-credit-transfers",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "1",
                "192.168.0.0",
                "1",
                "1",
                "1",
                "1",
                "1",
                FormattedDate.formatDate()!!,
                "1",
                debtorAccount,
                transactionFees,
                creditorAccount,
                "ABCDEFABC0A",
                userName,
                creditorAddress,
                frequencyToSubmit,
                executionDate)

            ibanText.setText("")
            valueText.setText("")
            dropdown.editText?.setText("")
            dropdownDate.editText?.setText("")
        }

    }

    /**
     * Getting payment api cal response
     * @param payment
     * @param message
     **/
    override fun onReadyPostPayment(payment: Payment?, message: String) {
        if (payment !== null) {
            database = FirebaseDatabase.getInstance().reference
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val uid = user.uid
                database.
                child("payments").
                child(uid).push().
                setValue(payment.transactionFees)
            }
            Toast.makeText(requireContext(),
                "Transferência efetuada com sucesso", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onReadyPostMBPayment(payment: Payment?, message: String) {
        Log.d("MB Payment", message)
    }

    /**
     * Loading the user's first and last name
     **/
    private fun loadUserInfo() {
        val user = mAuth?.currentUser
        val uid = user!!.uid
        val mDatabase = FirebaseDatabase
            .getInstance().getReference("users")

        mDatabase.child(uid).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(requireContext(), p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val firstName: String = snapshot.child("firstName").value.toString()
                val lastName: String = snapshot.child("lastName").value.toString()

                userName = firstName + lastName
            }
        })
    }

    /**
     * Getting user accounts for list display
     **/
    private fun getAccounts() {
        val mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user!!.uid
        val accountRef = FirebaseDatabase
            .getInstance().getReference("accounts")
        accountRef.child(uid).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                arrayOfAccounts = arrayListOf()
                for (key in snapshot.children) {
                    val account = key.getValue(Account::class.java)
                    if (!arrayOfAccounts.contains(account)) {
                        arrayOfAccounts.add(account!!)
                        val autoCompleteList = arrayOfAccounts.
                        map { allAccounts -> allAccounts.iban }
                        val adapter =
                            ArrayAdapter(requireContext(), R.layout.account_list, autoCompleteList)
                        dropdown = view!!.findViewById(R.id.dropdown_account)
                        (dropdown_account.editText as? AutoCompleteTextView)?.setAdapter(adapter)
                    }
                }
            }
        })

    }

    /**
     * Filling the frequency dropdown
     * @param view
     **/
    private fun setFrequency(view: View) {
        frequency.add("Diária")
        frequency.add("Semanal")
        frequency.add("Mensal")
        frequency.add("Trimestral")
        frequency.add("Semestral")
        frequency.add("Anual")
        val adapter =
            ArrayAdapter(requireContext(), R.layout.date_list, frequency)
        dropdownDate = view.findViewById(R.id.dropdown_date)
        (dropdownDate.editText as? AutoCompleteTextView)?.setAdapter(adapter)

    }

    /**
     * Checkbox click handler
     * @param view
     **/
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            when (view.id) {
                R.id.checkbox -> {
                    if (checked) {
                        dropdownDate.visibility = View.VISIBLE
                    } else {
                        dropdownDate.visibility = View.GONE
                    }
                }
            }
        }
    }

}
