package com.mahmoudjoe3.khabeertask.data.api.dao

import com.mahmoudjoe3.khabeertask.data.api.response.PayrollResponse
import retrofit2.http.GET

//
// Base url :: http://40.127.194.127:5656/Salamtak/
interface PayrollDao {
    @GET("GetPayroll")
    suspend fun getPayroll(): PayrollResponse
}