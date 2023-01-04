package com.mahmoudjoe3.khabeertask.viewModel

import androidx.lifecycle.ViewModel
import com.mahmoudjoe3.khabeertask.data.repository.PayrollRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PayrollViewModel @Inject constructor(private val payrollRepository: PayrollRepository )
    : ViewModel() {
    var payroll = payrollRepository.getPayroll()

    fun getPayroll(){
        payroll = payrollRepository.getPayroll()
    }
}