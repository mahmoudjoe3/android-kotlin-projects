package com.mahmoudjoe3.khabeertask.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import com.androidplot.pie.PieChart
import com.androidplot.pie.PieRenderer
import com.androidplot.pie.Segment
import com.androidplot.pie.SegmentFormatter
import com.mahmoudjoe3.khabeertask.R
import com.mahmoudjoe3.khabeertask.data.pojo.PayrollModel
import com.mahmoudjoe3.khabeertask.databinding.ActivityLoginBinding
import com.mahmoudjoe3.khabeertask.databinding.ActivityMainBinding
import com.mahmoudjoe3.khabeertask.viewModel.PayrollViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel
import java.text.DecimalFormat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:PayrollViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onResume() {
        observePayroll()
        super.onResume()
    }
    private fun observePayroll() {
        //viewModel.getPayroll()
        viewModel.payroll.observe(this){ payrollResponse ->
            val payRoll = payrollResponse.Payroll
            initUI(payRoll)
        }
    }

    private fun initUI(payRoll: PayrollModel) {
        binding.name.text = payRoll.Employee[0].EMP_DATA_AR
        binding.jobName.text = payRoll.Employee[0].JOBNAME_AR
        binding.totalSalary.text = String.format("${calculateSalary(payRoll)}ج ")
        binding.date.text = payRoll.Date

        binding.baseSalary.text = String.format("${payRoll.Allowences[0].SAL_VALUE}ج ")
        binding.addedSalary.text = String.format("${payRoll.Allowences[1].SAL_VALUE}ج ")
        binding.deduction.text = String.format("${payRoll.Deduction[0].SAL_VALUE}ج ")

        binding.allowVal.text = String.format("${calculateAllowence(payRoll.Allowences)}ج ")
        binding.deductVal.text = String.format("${calculateDeduction(payRoll.Deduction)}ج ")
        binding.totalSalVal.text = String.format("${calculateSalary(payRoll)}ج ")

        generatePiChart(calculateAllowence(payRoll.Allowences),calculateDeduction(payRoll.Deduction))

    }

    private fun generatePiChart(allowences: Double, deductions: Double) {
        val allowPercent =DecimalFormat("#.#")
            .format((allowences/(allowences+deductions))*100)
        val deductPercent =DecimalFormat("#.#")
            .format((deductions/(allowences+deductions))*100)
        binding.pieChart.addPieSlice(
            PieModel(
                allowPercent,allowPercent.toFloat(),Color.parseColor("#556080")
            )
        )
        binding.pieChart.addPieSlice(
            PieModel(
                deductPercent,deductPercent.toFloat(),Color.parseColor("#f0c419")
            )
        )
        binding.pieChart.startAnimation()
    }

    private fun calculateSalary(payRoll: PayrollModel): String {
        return DecimalFormat("#.##")
            .format(calculateDeduction(payRoll.Deduction)-calculateAllowence(payRoll.Allowences))
    }

    private fun calculateAllowence(allowences: List<PayrollModel.Allowence>): Double {
        var allowenceSum = 0.0
        for (allow in allowences) {
            allowenceSum += allow.SAL_VALUE
        }
        return allowenceSum
    }

    private fun calculateDeduction(deduction: List<PayrollModel.Deduct>): Double {
        var deductionSum=0.0
        for (deduct in deduction){
            deductionSum+=deduct.SAL_VALUE
        }
        return deductionSum
    }
}