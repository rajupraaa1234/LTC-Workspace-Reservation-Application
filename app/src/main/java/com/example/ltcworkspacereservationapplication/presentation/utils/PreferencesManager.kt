package com.example.ltcworkspacereservationapplication.presentation.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREF_NAME = "LTC_APPLICATION"
    private const val KEY_EMPLOYEE_ID = "KEY_EMPLOYEE_ID"

    private const val EMP_NAME = "EMP_NAME"

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


    fun setEmployeeName(context: Context, employeeName: String) {
        val editor = getPreferences(context).edit()
        editor.putString(EMP_NAME, employeeName)
        editor.apply()
    }

    fun getEmployeeName(context: Context): String? {
        return getPreferences(context).getString(EMP_NAME, null)
    }

    fun clearEmployeeId(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_EMPLOYEE_ID)
        editor.remove(EMP_NAME)
        editor.apply()
    }
}