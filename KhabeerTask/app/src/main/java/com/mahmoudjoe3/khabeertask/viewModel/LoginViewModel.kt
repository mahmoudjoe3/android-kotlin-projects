package com.mahmoudjoe3.khabeertask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.mahmoudjoe3.khabeertask.data.api.response.LoginResponse
import com.mahmoudjoe3.khabeertask.data.pojo.UserModel
import com.mahmoudjoe3.khabeertask.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository)
    :ViewModel() {

    fun logIn(user: UserModel):LiveData<LoginResponse> {
        return loginRepository.postUser(user)
    }
    fun storeAuthToken(token: String){
        GlobalScope.launch {
            loginRepository.getUserPreferences().saveAuthToken(token);
        }
    }
    fun getToken():Flow<String?> = loginRepository.getUserPreferences().authToken


}