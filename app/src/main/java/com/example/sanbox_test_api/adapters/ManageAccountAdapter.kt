package com.example.sanbox_test_api.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.models.Account

class ManageAccountAdapter (
    private val listOfAccounts: ArrayList<Account>
) : RecyclerView.Adapter<ManageAccountAdapter.ViewHolder>() {

    /**
     * Inflate view
     * @param parent
     * @param viewType
     **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.accounts_list, parent, false)
        return ViewHolder(view)
    }

    /**
     * Bind view
     * @param holder
     * @param position
     **/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val account = listOfAccounts[position]
        holder.iban.text = account.iban
        holder.name.text = account.msisdn
    }

    /**
     * Get transactions size
     **/
    override fun getItemCount(): Int = listOfAccounts.size

    /**
     * @class View holder
     * @param itemsView
     **/
    class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView) {
        val iban: TextView = itemsView.findViewById(R.id.iban)
        val name: TextView = itemsView.findViewById(R.id.name)
    }

    fun removeAt(position: Int) {
        listOfAccounts.removeAt(position)
        notifyItemRemoved(position)
    }
}