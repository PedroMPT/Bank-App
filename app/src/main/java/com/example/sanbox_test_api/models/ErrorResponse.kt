package com.example.sanbox_test_api.models

import com.example.sanbox_test_api.utils.JSONConvertable
import com.google.gson.annotations.SerializedName

/**
 * @class ErrorResponse
 **/
data class ErrorResponse (
    @SerializedName("category")
    var category: String = "",
    @SerializedName("code")
    var code: String = "",
    @SerializedName("text")
    var text: String
): JSONConvertable