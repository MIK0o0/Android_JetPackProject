package com.example.jetpackproject.Data

import android.content.Context

fun saveBooleanValue(context: Context, key: String, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun getBooleanValue(context: Context, key: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, true) // Default value is true
}

fun saveStringValue(context: Context, key: String, value: String) {
    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun getStringValue(context: Context, key: String): String? {
    val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, null)
}

