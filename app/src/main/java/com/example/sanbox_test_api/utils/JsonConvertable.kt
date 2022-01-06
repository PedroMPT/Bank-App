package com.example.sanbox_test_api.utils

import com.google.gson.Gson
import org.json.JSONArray

/**
 * @interface JSONConvertable
 **/
interface JSONConvertable {
    fun toJSON(): String = Gson().toJson(this)
}

/**
 * @fun JSON to Class
 **/
inline fun <reified T : JSONConvertable> String.toObject(): T =
    Gson().fromJson(this, T::class.java)

/**
 * @fun JSONArray to ArrayList
 **/
inline fun <reified T : JSONConvertable> JSONArray.toArrayList(): ArrayList<T> {
    val array: ArrayList<T> = arrayListOf()
    for (i in 0 until this.length()) {
        array.add(this.getJSONObject(i).toString().toObject())
    }
    return array
}
