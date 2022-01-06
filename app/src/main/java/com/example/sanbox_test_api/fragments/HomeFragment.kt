package com.example.sanbox_test_api.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.activities.AddAccount
import com.example.sanbox_test_api.activities.ManageAccounts
import com.example.sanbox_test_api.activities.TransactionActivity
import com.example.sanbox_test_api.adapters.AccountAdapter
import com.example.sanbox_test_api.adapters.ListAdapter
import com.example.sanbox_test_api.models.Account
import com.example.sanbox_test_api.models.Transaction
import com.example.sanbox_test_api.utils.JSONConvertable
import com.example.sanbox_test_api.utils.SharedViewModel
import com.example.sanbox_test_api.utils.toObject
import com.example.sanbox_test_api.views.AccountListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment(R.layout.home_fragment),
    AccountListener,
    JSONConvertable {

    /**
     * UI variables
     **/
    private val adapter: AccountAdapter = AccountAdapter(this)
    private lateinit var viewPagerAccounts: ViewPager
    private lateinit var emptyCardView: CardView
    private lateinit var emptyTransactions: TextView
    private lateinit var balanceText: TextView
    private lateinit var moreOptions: ImageView
    private lateinit var transactionsRecycler: RecyclerView
    private var sliderDotsPanel: LinearLayout? = null

    /**
     * Accounts array
     **/
    private var accountList: ArrayList<Account> = arrayListOf()

    /**
     * Transactions array
     **/
    private var transactionsList: ArrayList<Transaction> = arrayListOf()

    /**
     * Card view dots count
     **/
    private var dotsCount = 0

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
        val view = inflater.inflate(R.layout.home_fragment, container, false)


        viewPagerAccounts = view.findViewById(R.id.account_view_pager)
        sliderDotsPanel = view.findViewById(R.id.slider_dots)
        emptyCardView = view.findViewById(R.id.empty_view_pager)
        emptyTransactions = view.findViewById(R.id.empty_transactions)
        balanceText = view.findViewById(R.id.wallet_balance_text)
        moreOptions = view.findViewById(R.id.more)
        transactionsRecycler = view.findViewById(R.id.list_recycler_view)

        moreOptions.setOnClickListener{
            v -> showPopup(v)
        }

        getAccounts()


        return view
    }

    /**
     * Getting firebase accounts for card display
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
                accountList = arrayListOf()
                for (key in snapshot.children) {
                    val account = key.getValue(Account::class.java)
                    if (!accountList.contains(account)) {
                        accountList.add(account!!)
                    }
                }

                if (accountList.size > 0) {
                    setCards()
                    getWalletBalance()
                    val accountId = accountList[0].id
                    getTransactions(accountId)

                } else {
                    emptyCardView.visibility = View.VISIBLE
                    emptyTransactions.visibility = View.VISIBLE
                }
            }
        })

    }

    /**
     * Method to handle menu popup
     * @param v
     **/
    private fun showPopup(v: View) {
        PopupMenu(context, v).apply {
            this.setOnMenuItemClickListener { item -> when (item.itemId) {
                R.id.add -> {
                    activity?.let{
                        val intent = Intent (it, AddAccount::class.java)
                        it.startActivity(intent)
                    }
                    true
                }
                R.id.remove -> {
                    activity?.let{
                        val intent = Intent (it, ManageAccounts::class.java)
                        it.startActivity(intent)
                    }
                    true
                }
                else -> false
            }

            }
            inflate(R.menu.actions)
            show()
        }
    }

    /**
     * Getting the total balance of user account
     **/
    private fun getWalletBalance() {
        val balance =  accountList.map { account -> account.currency.toInt() }
        val sumOfBalances = balance.reduce { acc, i -> acc + i }
        balanceText.text = sumOfBalances.toString()
        model.setBalanceAmount(sumOfBalances)
    }

    /**
     * Setting the cards
     **/
    private fun setCards() {

        emptyCardView.visibility = View.GONE
        emptyTransactions.visibility = View.GONE
        adapter.setAccountCard(accountList, context)
        viewPagerAccounts.adapter = adapter
        dotsCount = adapter.count
        val dots = arrayOfNulls<ImageView>(dotsCount)
        setDots(dots)

        viewPagerAccounts.addOnPageChangeListener(object: OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int){}

            override fun onPageSelected(position: Int) {
                setValuesForTransactionRequest(position)

                for (i in 0 until dotsCount) {
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,R.drawable.non_active_dot)
                    )
                }
                dots[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,R.drawable.active_dot)
                )
            }
        })

        model.setTotal(accountList.size)
    }

    /**
     * Setting the swipe dots
     **/
    private fun setDots(dots: Array<ImageView?>) {
        for (i in 0 until dotsCount) {
            dots[i] = ImageView(context)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(requireContext(),
                    R.drawable.non_active_dot))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            sliderDotsPanel!!.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(), R.drawable.active_dot)
        )
    }

    private fun setValuesForTransactionRequest(position: Int) {
        val accountId = accountList[position].id
        getTransactions(accountId)
    }

    /**
     * Method to handle the swiped accounts transactions
     * @param accountId
     **/
    private fun getTransactions(accountId: String) {
        val transactionRef = FirebaseDatabase
            .getInstance().getReference("transactions").child(accountId)
        transactionRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                transactionsList = arrayListOf()
                for (key in snapshot.children) {
                    val transaction =
                        key.value as ArrayList<HashMap<String, Any?>>
                    for (value in transaction) {
                        val finalTransaction = Transaction(
                            value.getValue("transactionId").toString(),
                            "",
                            "",
                            "",
                            value.getValue("bookingDate").toString(),
                            value.getValue("valueDate").toString(),
                            value.getValue("amount").toString().toObject(),
                            value.getValue("creditorName").toString(),
                            null,
                            "",
                            "",
                            null,
                            "",
                            value.getValue("remittanceInformationUnstructured").toString(),
                            "",
                            ""
                        )
                        transactionsList.add(finalTransaction)
                    }
                    transactionsList.map { value -> value
                        .purposeCode = value.amount!!.content }
                    transactionsList.sortByDescending { transactionDate ->
                        transactionDate.valueDate }
                    transactionsRecycler.layoutManager = LinearLayoutManager(context)
                    transactionsRecycler.adapter = ListAdapter(transactionsList)
                }
            }
        })
    }

    /**
     * On card click method for account details
     * @param account
     **/
    override fun onCardClickListener(account: Account) {
        if (account != null) {
            model.setAccount(account)
            val intent = Intent(requireContext(), TransactionActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, account.toJSON())
            startActivity(intent)
        }
    }

}
