package com.example.sanbox_test_api.api

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.gson.Gson


class ApiRequest (private val ctx: Context) {

    /***
     * Volley method that'll perform the api request with the specific params, header's
     * and body if needed
     * The ApiResponse will handle the response itself
     * @param route
     * @param completion
     */
    fun performRequest(route: ApiRoute,
                       completion: (success: Boolean, apiResponse: ApiResponse) -> Unit) {
        val request: StringRequest = object : StringRequest(
            route.httpMethod,
            route.url,
            { response -> this.handle(response, completion) },
            { it.printStackTrace()
                if (it.networkResponse != null && it.networkResponse.data != null)
                    this.handle(String(it.networkResponse.data), completion)
                else
                    this.handle(getStringError(it), completion)
            })

        {
            override fun getParams(): MutableMap<String, String>? {
                return route.params
            }

            override fun getHeaders(): MutableMap<String, String> {
                return route.headers
            }

            override fun getBody(): ByteArray? {
                val json = Gson().toJson(route.body)
                return json.toByteArray()
            }

        }
        request.retryPolicy = DefaultRetryPolicy(route.timeOut,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        getRequestQueue().add(request)
    }

    /**
     * This method will make the creation of the answer as ApiResponse
     * @param response
     * @param completion
     **/
    private fun handle(response: String,
                       completion: (success: Boolean, apiResponse: ApiResponse) -> Unit) {
        val ar = ApiResponse(response)
        completion.invoke(ar.success, ar)
    }

    /**
     * This method will return the error as String
     * @param volleyError
     **/
    private fun getStringError(volleyError: VolleyError): String {
        return when (volleyError) {
            is TimeoutError -> "The connection timed out."
            is NoConnectionError -> "The connection couldn't be established."
            is AuthFailureError -> "There was an authentication failure in your request."
            is ServerError -> "Error while processing the server response."
            is NetworkError -> "Network error, please verify your connection."
            is ParseError -> "Error while processing the server response."
            else -> "Internet error"
        }
    }
    /**
     * We create and return a new instance for the queue of Volley requests.
     **/
    private fun getRequestQueue(): RequestQueue {
        val maxCacheSize = 20 * 1024 * 1024
        val cache = DiskBasedCache(ctx.cacheDir, maxCacheSize)
        val netWork = BasicNetwork(HurlStack())
        val mRequestQueue = RequestQueue(cache, netWork)
        mRequestQueue.start()
        System.setProperty("http.keepAlive", "false")
        return mRequestQueue
    }
}