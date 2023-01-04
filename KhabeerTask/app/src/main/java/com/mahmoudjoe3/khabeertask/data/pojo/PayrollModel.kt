package com.mahmoudjoe3.khabeertask.data.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayrollModel(
    val Employee: List<EmployeeModel>,
    val Allowences: List<Allowence>,
    val Deduction: List<Deduct>,
    val Date: String
): Parcelable{
    @Parcelize
    data class EmployeeModel(
        val EMP_ID: Int,
        val EMP_DATA_AR: String,
        val JOBNAME_AR: String
    ):Parcelable

    @Parcelize
    data class Allowence(
        val COMP_DESC_AR: String,
        val SAL_VALUE: Double
    ):Parcelable

    @Parcelize
    data class Deduct(
        val COMP_DESC_AR: String,
        val SAL_VALUE: Double
    ):Parcelable
}
