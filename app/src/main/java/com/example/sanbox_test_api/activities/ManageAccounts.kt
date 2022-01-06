package com.example.sanbox_test_api.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.adapters.ManageAccountAdapter
import com.example.sanbox_test_api.models.Account
import com.example.sanbox_test_api.utils.SwipeToDeleteCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_manage_accounts.*

class ManageAccounts : AppCompatActivity() {

    /**
     * Accounts array
     **/
    private var accountList: ArrayList<Account> = arrayListOf()

    /**
     * On create lifecycle
     * @param savedInstanceState
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_accounts)
        getAccounts()

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            /**
             * Method to delete account from firebase
             * @param viewHolder
             * @param
             **/
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter =
                    account_list_recycler_view.adapter as ManageAccountAdapter
                val mAuth = FirebaseAuth.getInstance()
                val user = mAuth.currentUser
                val uid = user!!.uid
                val accountToDelete = FirebaseDatabase
                    .getInstance().getReference("accounts")
                val transactionToDelete = FirebaseDatabase
                    .getInstance().getReference("transactions")
                val account = accountList.elementAt(viewHolder.adapterPosition)
                val accountID = account.id
                adapter.removeAt(viewHolder.adapterPosition)
                accountToDelete.child(uid).addListenerForSingleValueEvent(
                    object: ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(snap: DataSnapshot) {
                        for (child in snap.children) {
                            val accountFromFirebase = child
                                .getValue(Account::class.java)!!
                            if (accountID === accountFromFirebase.id) {
                               val key = child.key!!
                               accountToDelete.child(uid).child(key).removeValue()
                                this@ManageAccounts.let{
                                    val intent = Intent (it, MainActivity::class.java)
                                    intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    it.startActivity(intent)
                                    finish()
                                }
                               transactionToDelete.child(accountID).removeValue()
                               Toast.makeText(this@ManageAccounts,
                                   "Conta eliminada", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(account_list_recycler_view)
    }

    /**
     * Getting accounts from firebase
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
                for (key in snapshot.children) {
                    val account = key.getValue(Account::class.java)
                    if (!accountList.contains(account)) {
                        accountList.add(account!!)
                    }
                }
                account_list_recycler_view.layoutManager =
                    LinearLayoutManager(this@ManageAccounts)
                account_list_recycler_view.adapter = ManageAccountAdapter(accountList)
            }
        })

    }
}
