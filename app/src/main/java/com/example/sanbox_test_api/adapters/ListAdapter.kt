package com.example.sanbox_test_api.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.models.Transaction

class ListAdapter(
    private val listTransactions: ArrayList<Transaction>
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * Inflate view
     * @param parent
     * @param viewType
     **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.transactions_list, parent, false)
        return ViewHolder(view)
    }

    /**
     * Bind view
     * @param holder
     * @param position
     **/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transaction = listTransactions[position]
        holder.transactionId.text = transaction.remittanceInformationUnstructured
        holder.date.text = transaction.valueDate
        holder.amount.text = transaction.purposeCode

        if (transaction.purposeCode.startsWith('+')) {
            holder.amount.setTextColor(Color.parseColor("#00e676"));
        } else {
            holder.amount.setTextColor(Color.parseColor("#ff3d00"));
        }
    }

    /**
     * Get transactions size
     **/
    override fun getItemCount(): Int = listTransactions.size

    /**
     * @class View holder
     * @param itemsView
     **/
    class ViewHolder(itemsView: View): RecyclerView.ViewHolder(itemsView) {
        val amount: TextView = itemsView.findViewById(R.id.amount)
        val date: TextView = itemsView.findViewById(R.id.date)
        val transactionId: TextView = itemsView.findViewById(R.id.transactionId)
    }
}