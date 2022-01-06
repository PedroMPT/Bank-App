package com.example.sanbox_test_api.api

import com.example.sanbox_test_api.models.ErrorResponse
import com.example.sanbox_test_api.utils.toArrayList
import org.json.JSONObject
import org.json.JSONTokener

class ApiResponse(response: String) {

    /**
     * The api has been successful
     **/
    var success: Boolean = false

    /**
     * Response status message
     **/
    var message: String = ""

    /**
     * Response json object
     **/
    var json: JSONObject = JSONObject()

    /**
     * Api response handling
     **/
    init {
        try {
            val jsonToken = JSONTokener(response).nextValue()
            if (jsonToken is JSONObject) {
                val jsonResponse = JSONObject(response)
                if (jsonResponse.has("tppMessages")) {
                    success = false
                    val errorResponse: ArrayList<ErrorResponse> =
                        jsonResponse.getJSONArray("tppMessages").toArrayList()
                    for (error: ErrorResponse in errorResponse) {
                        message = error.text
                    }
                }
                else {
                    success = true
                    message = "200 OK"
                    json = jsonResponse
                }
            } else {
                message = response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}