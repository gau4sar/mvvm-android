package com.gaurav.fretello.utils

import android.content.Context
import android.text.TextUtils
import com.gaurav.fretello.utils.Constants.APP_PACKAGE_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PreferenceUtils(private val context: Context){

    companion object {
        private const val PREFS_NAME = "$APP_PACKAGE_NAME.PREFS"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun <T : Any> get(key: String, default: T): T {
        val type = default::class.java
        if (preferences != null) {
            return when (default) {
                is String -> type.cast(preferences.getString(key, default))
                is Long -> type.cast(preferences.getLong(key, default))
                is Int -> type.cast(preferences.getInt(key, default))
                is Float -> type.cast(preferences.getFloat(key, default))
                is Boolean -> type.cast(preferences.getBoolean(key, default))
                else -> default
            }
        }
        return default
    }

    fun <T : Any> set(key: String, value: T): Boolean {
        if (preferences != null && !TextUtils.isEmpty(key)) {
            val editor = preferences.edit()
            when (value) {
                is String -> editor.putString(key, value)
                is Long -> editor.putLong(key, value)
                is Int -> editor.putInt(key, value)
                is Float -> editor.putFloat(key, value)
                is Boolean -> editor.putBoolean(key, value)
                else -> return false
            }
            editor.apply()
            return true
        }
        return false
    }

    fun <T> setMutableList(key: String, list: MutableList<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    fun getMutableList(key: String): MutableList<Int>? {
        val gson = Gson()
        val mutableList: MutableList<Int>
        val string: String = getStringPreference(key, "null")
        val type: Type = object : TypeToken<MutableList<Int?>?>() {}.type
        mutableList = gson.fromJson<MutableList<Int>>(string, type)
        return mutableList
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun getStringPreference(key: String, defaultValue: String = ""): String = get(key, defaultValue)
    fun getLongPreference(key: String, defaultValue: Long = 0L): Long = get(key, defaultValue)
    fun getIntegerPreference(key: String, defaultValue: Int = 0): Int = get(key, defaultValue)
    fun getBooleanPreference(key: String, defaultValue: Boolean = false): Boolean =
        get(key, defaultValue)

    fun setStringPreference(key: String, value: String): Boolean = set(key, value)
    fun setLongPreference(key: String, value: Long): Boolean = set(key, value)
    fun setIntegerPreference(key: String, value: Int): Boolean = set(key, value)
    fun setBooleanPreference(key: String, value: Boolean): Boolean = set(key, value)
}