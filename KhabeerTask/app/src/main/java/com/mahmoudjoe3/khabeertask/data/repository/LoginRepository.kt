package com.mahmoudjoe3.khabeertask.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudjoe3.khabeertask.data.DataStoreManager
import com.mahmoudjoe3.khabeertask.data.api.dao.LoginDao
import com.mahmoudjoe3.khabeertask.data.api.response.LoginResponse
import com.mahmoudjoe3.khabeertask.data.pojo.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val loginDao: LoginDao,private val dataStoreManager: DataStoreManager) {

    fun getUserPreferences():DataStoreManager = dataStoreManager

    fun postUser(userModel: UserModel):LiveData<LoginResponse> {
        val loginResponseLiveData: MutableLiveData<LoginResponse> = MutableLiveData()
        GlobalScope.launch(Dispatchers.IO) {
            val response: LoginResponse
            try {
                response = loginDao.postUser(userModel)
                withContext(Dispatchers.Main) {
                    loginResponseLiveData.value = response
                }
            } catch (ex: Exception) {
                Log.d("TAG", "Login: " + ex.message)
            }
        }
        return loginResponseLiveData
    }



}