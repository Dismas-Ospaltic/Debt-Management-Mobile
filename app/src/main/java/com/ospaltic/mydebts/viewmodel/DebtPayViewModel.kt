package com.ospaltic.mydebts.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ospaltic.mydebts.model.RepayEntity
import com.ospaltic.mydebts.repository.DebtRepayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DebtPayViewModel(private val repository: DebtRepayRepository) : ViewModel() {

    private val _debtRepayList = MutableStateFlow<List<RepayEntity>>(emptyList())
    val debtRepayList: StateFlow<List<RepayEntity>> get() = _debtRepayList

    private val _debtHistoryList = MutableStateFlow<List<RepayEntity>>(emptyList())
    val debtHistoryList: StateFlow<List<RepayEntity>> get() = _debtHistoryList

    // Fetch repayments for a specific debt
    fun getDebtRepayById(debtId: String) {
        viewModelScope.launch {
            repository.getDebtRepayById(debtId).collectLatest { repayments ->
                _debtRepayList.value = repayments
            }
        }
    }

    // Fetch repayment history for a user
    fun getAllDebtHist(userId: String) {
        viewModelScope.launch {
            repository.getAllDebtHist(userId).collectLatest { history ->
                _debtHistoryList.value = history
            }
        }
    }

    // Insert a new repayment record
    fun insertRepayment(repay: RepayEntity) {
        viewModelScope.launch {
            repository.insert(repay)
        }
    }

    // Update an existing repayment record
    fun updateRepayment(repay: RepayEntity) {
        viewModelScope.launch {
            repository.update(repay)
        }
    }


    private val _totalPaid = MutableStateFlow(0.0f)
    val totalPaid: StateFlow<Float> = _totalPaid

    fun fetchTotalPaid(debtId: String) {
        viewModelScope.launch {
            repository.getTotalPaid(debtId).collectLatest { total ->
                Log.d("DebtPayViewModel", "Total Paid Updated: $total") // Debugging
                _totalPaid.value = total
            }
        }
    }


//    private val _totalPaid = MutableStateFlow(0.0f)
//    val totalPaid: StateFlow<Float> = _totalPaid
//
//    fun fetchTotalPaid(debtId: String) {
//        viewModelScope.launch {
//            repository.getTotalPaid(debtId).collectLatest { total ->
//                Log.d("DebtPayViewModel", "Total Paid Updated: $total") // Debugging
//                _totalPaid.value = total
//            }
//        }
//    }

//    private val _totalPaid = MutableStateFlow(0.0f)
//    val totalPaid: StateFlow<Float> = _totalPaid
//
//    fun fetchTotalPaid(debtId: String) {
//        viewModelScope.launch {
//            val total = repository.getTotalPaid("")
//            Log.d("DebtPayViewModel", "Total Paid Updated: $total")
//            _totalPaid.value = total
//        }
//    }

}