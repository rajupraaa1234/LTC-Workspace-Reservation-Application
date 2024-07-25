package com.example.ltcworkspacereservationapplication.presentation.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREF_NAME = "LTC_APPLICATION"
    private const val KEY_EMPLOYEE_ID = "KEY_EMPLOYEE_ID"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setEmployeeId(context: Context, employeeId: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_EMPLOYEE_ID, employeeId)
        editor.apply()
    }

    fun getEmployeeId(context: Context): String? {
        return getPreferences(context).getString(KEY_EMPLOYEE_ID, null)
    }

    fun clearEmployeeId(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_EMPLOYEE_ID)
        editor.apply()
    }
}