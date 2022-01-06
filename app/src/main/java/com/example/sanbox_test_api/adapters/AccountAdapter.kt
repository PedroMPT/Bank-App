package com.example.sanbox_test_api.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.models.Account
import com.example.sanbox_test_api.views.AccountListener

class AccountAdapter(private val accountListener: AccountListener) : PagerAdapter() {

    /**
     * UI variables
     **/
    private lateinit var accounts: ArrayList<Account>
    private lateinit var layoutInflater: LayoutInflater

    /**
     * View and context
     **/
    lateinit var view: View
    private var context: Context? = null

    /**
     * Set cards
     * @param accounts
     * @param context
     **/
    fun setAccountCard(
        accounts: ArrayList<Account>,
        context: Context?
    ) {
        this.accounts = accounts
        this.context = context
    }

    /**
     * Handle cards
     * @param view
     * @param `object`
     **/
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    /**
     * Cards size
     **/
    override fun getCount(): Int {
        return accounts.size
    }

    /**
     * Cards position
     **/
    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    /**
     * Instantiate cards
     * @param container
     * @param position
     **/
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater
            .inflate(R.layout.account_card, container, false)
        val name: TextView
        val bank: TextView
        val iban: TextView
        val balance: TextView
        name = view.findViewById(R.id.name)
        iban = view.findViewById(R.id.iban)
        balance = view.findViewById(R.id.balance)
        bank = view.findViewById(R.id.bank)
        name.text = accounts[position].name
        iban.text = accounts[position].iban
        bank.text = accounts[position].msisdn
        val currency = accounts[position].currency
        val balanceText = "€ $currency"
        balance.text = balanceText
        val accountsList = accounts[position]
        view.setOnClickListener {
            accountListener.onCardClickListener(accountsList)
        }
        container.addView(view, 0)
        return view
    }

    /**
     * Destroy card
     * @param container
     * @param position
     * @param `object`
     **/
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}