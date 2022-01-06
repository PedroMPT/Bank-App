package com.example.sanbox_test_api.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanbox_test_api.R
import com.example.sanbox_test_api.adapters.ListAdapter
import com.example.sanbox_test_api.models.Account
import com.example.sanbox_test_api.models.Transaction
import com.example.sanbox_test_api.utils.CustomMarker
import com.example.sanbox_test_api.utils.JSONConvertable
import com.example.sanbox_test_api.utils.toObject
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_transaction.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TransactionActivity : AppCompatActivity(), JSONConvertable {

    /**
     * Transactions list
     **/
    private var transactionsList: ArrayList<Transaction> = arrayListOf()

    /**
     * Account received from intent
     **/
    private lateinit var accountId: String

    /**
     * On create lifecycle
     * @param savedInstanceState
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val message = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (message !== null) {
            accountId = message.toString().toObject<Account>().id
        }

        getTransactions(accountId)
    }

    /**
     * Getting transactions from a specific account
     * @param accountId
     **/
    private fun getTransactions(accountId: String) {
        val transactionRef = FirebaseDatabase
            .getInstance().getReference("transactions").child(accountId)
        transactionRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
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
                    transactionsList.map { value ->
                        value.purposeCode = value.amount!!.content }
                    transactionsList.sortByDescending { transactionDate ->
                        transactionDate.valueDate }
                    list_transaction_view.layoutManager =
                        LinearLayoutManager(this@TransactionActivity)
                    list_transaction_view.adapter = ListAdapter(transactionsList)
                    createGraph()
                }
            }
        })
    }

    /**
     * Graph transactions builder
     **/
    private fun createGraph() {

        //Part1
        val entries = ArrayList<Entry>()
        transactionsList.sortByDescending { transaction -> transaction.valueDate }
        transactionsList.take(1)

        for (transaction in transactionsList) {
            val string = transaction.valueDate
            val date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE)
            if (date.year == 2018) {
                transaction.purposeCode.replaceFirst("^0+(?!$)", "")
                entries.add(Entry(date.monthValue.toFloat(), transaction.purposeCode.toFloat()))
            }

        }


        //Part3
        val vl = LineDataSet(entries, "Movimentos")

        //Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.gray
        vl.fillAlpha = R.color.red

        //Part5
        lineChart.xAxis.labelRotationAngle = 0f

        //Part6
        lineChart.data = LineData(vl)

        //Part7
        lineChart.axisRight.isEnabled = false
        //lineChart.xAxis.axisMaximum = 0.1f

        //Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        //Part9
        lineChart.description.text = "Meses"
        lineChart.setNoDataText("Sem dados a apresentar")

        //Part10
        lineChart.animateX(1800, Easing.EaseInExpo)

        //Part11
        val markerView = CustomMarker(this@TransactionActivity, R.layout.marker_view)
        lineChart.marker = markerView
    }
}