package com.example.sanbox_test_api.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.activities.LoginActivity
import com.example.sanbox_test_api.utils.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserFragment : Fragment(R.layout.user_fragment) {

    /**
     * Firebase authentication variable
     **/
    private var mAuth: FirebaseAuth? = null

    /**
     * UI variables
     **/
    private lateinit var sign_out: TextView
    private lateinit var username: TextView
    private lateinit var userName: String
    private lateinit var email: TextView
    private lateinit var balanceText: TextView
    private lateinit var accountTotalText: TextView
    private lateinit var paymentsTotal: TextView

    /**
     * Fragment share view model
     **/
    private val model: SharedViewModel by activityViewModels()

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

        val view = inflater.inflate(R.layout.user_fragment, container, false)

        mAuth = FirebaseAuth.getInstance()
        sign_out = view.findViewById(R.id.btn_sign_out)
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        balanceText = view.findViewById(R.id.balanceAmount)
        accountTotalText = view.findViewById(R.id.accountTotal)
        paymentsTotal = view.findViewById(R.id.payments)

        activity?.let {
            model.totalOfBalance.observe(it, Observer { balance ->
                balanceText.text = balance.toString()
            })
        }

        activity?.let {
            model.totalOfAccounts.observe(it, Observer { account ->
                accountTotalText.text = account.toString()
            })
        }


        loadUserInfo()

        sign_out.setOnClickListener { _ ->
            mAuth?.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            model.setTotal(0)
            model.setBalanceAmount(0)
        }

        setTotalOfPayments()
        return view
    }

    /**
     * Loading the user's info
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

                userName = "$firstName $lastName"
                username.text = userName
                email.text = user.email
            }
        })
    }

    /**
     * Getting the total of payments from firebase
     **/
    private fun setTotalOfPayments() {
        val mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user!!.uid
        val paymentRef = FirebaseDatabase
            .getInstance().getReference("payments")
        paymentRef.child(uid).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(snapshot: DataSnapshot) {
                paymentsTotal.text = snapshot.childrenCount.toInt().toString()
            }
        })
    }

}
