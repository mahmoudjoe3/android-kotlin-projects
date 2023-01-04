package com.mahmoudjoe3.khabeertask.data.api.dao

import com.mahmoudjoe3.khabeertask.data.api.response.LoginResponse
import com.mahmoudjoe3.khabeertask.data.pojo.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

// Base url :: http://40.127.194.127:5656/Salamtak/
interface LoginDao {

    @POST("LogIn")
    suspend fun postUser(@Body userModel: UserModel): LoginResponse

}