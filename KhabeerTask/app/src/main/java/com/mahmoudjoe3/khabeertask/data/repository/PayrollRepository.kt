package com.mahmoudjoe3.khabeertask.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudjoe3.khabeertask.data.api.dao.PayrollDao
import com.mahmoudjoe3.khabeertask.data.api.response.PayrollResponse
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayrollRepository @Inject constructor(private val payrollDao: PayrollDao) {

    fun getPayroll(): LiveData<PayrollResponse>{
        val payrollLiveData: MutableLiveData<PayrollResponse> = MutableLiveData()
        GlobalScope.launch(Dispatchers.IO){
            try {
                val payroll = payrollDao.getPayroll()
                withContext(Dispatchers.Main){
                    payrollLiveData.value = payroll
                }
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    payrollLiveData.value = null
                    Log.d("TAG", "getPayroll: "+ex.message)
                }
            }
        }
        return payrollLiveData
    }
}