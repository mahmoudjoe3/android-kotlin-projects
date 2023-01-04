package com.mahmoudjoe3.khabeertask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mahmoudjoe3.khabeertask.databinding.ActivityLoginBinding
import com.mahmoudjoe3.khabeertask.data.pojo.UserModel
import com.mahmoudjoe3.khabeertask.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel:LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //verifyUserAuthentication()

        binding.loginBtn.setOnClickListener {
            if(validateMobileNumber()&& validatePassword()) {
                login(
                    UserModel(
                        binding.mobileNumberEditText.text!!.toString(),
                        binding.passwordEditText.text!!.toString().toLong()
                    )
                )
            }
        }
    }
    private fun login(user: UserModel ) {
        loginViewModel.logIn(user).observe(this){loginResponse ->
            if(loginResponse.Success){
                Log.d("TAG", "login: token :: ${loginResponse.Token}")
                storeAuthToken(loginResponse.Token.toString())
                Toast.makeText(this, loginResponse.ArabicMessage, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun verifyUserAuthentication() {
        val token = runBlocking { loginViewModel.getToken().first() }
        if (token != null) {
            Log.d("TAG", "verifyUserAuthentication: $token")
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun storeAuthToken(token: String) {
        loginViewModel.storeAuthToken(token)
    }

    private fun validatePassword():Boolean {
        if (binding.passwordEditText.text!!.isEmpty())
            binding.passwordEditText.error = "Empty field!"
        else {
            binding.passwordEditText.error = null
            return true
        }
        return false
    }

    private fun validateMobileNumber():Boolean {
        if (binding.mobileNumberEditText.text!!.length != 11&&
            !binding.mobileNumberEditText.text!!.startsWith("01"))
            binding.mobileNumberEditText.error = "Enter valid mobile number!"
        else {
            binding.mobileNumberEditText.error = null
            return true
        }
        return false
    }
}