package com.example.sanbox_test_api.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

/**
 * @class Date format helper class
 **/
@RequiresApi(Build.VERSION_CODES.O)
class FormattedDate {

    companion object {

        fun formatDate(): String? {

            // "\"TUE, 11 FEB 2020 11:28:17 GMT\""
            val current = LocalDateTime.now()
            return "${current.dayOfWeek.name.take(3)}, " +
                    "${current.dayOfMonth} " +
                    "${current.month} " +
                    "${current.year} " +
                    "${current.hour}" +
                    ":" +
                    "${convertDate(current.minute)}" +
                    ":" +
                    "${convertDate(current.second)} " +
                    "GMT"
        }

        fun convertDate(input: Int): String? {
            return if (input >= 10) {
                input.toString()
            } else {
                "0$input"
            }
        }
    }
}